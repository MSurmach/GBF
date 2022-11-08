package com.godeltech.gbf;

import com.godeltech.gbf.config.TelegramBotConfig;
import com.godeltech.gbf.exception.DeleteMessageException;
import com.godeltech.gbf.factory.impl.InterceptorFactory;
import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.utils.BotMenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class GbfBot extends SpringWebhookBot {
    private final InterceptorFactory interceptorFactory;
    private final TelegramBotConfig telegramBotConfig;
    private final BotMessageService botMessageService;
    @Value("${bot.chmokiId}")
    private String chmokiId;

    public GbfBot(DefaultBotOptions options,
                  SetWebhook setWebhook,
                  InterceptorFactory interceptorFactory,
                  TelegramBotConfig telegramBotConfig,
                  BotMessageService botMessageService) {
        super(options, setWebhook);
        this.interceptorFactory = interceptorFactory;
        this.telegramBotConfig = telegramBotConfig;
        this.botMessageService = botMessageService;
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getBotToken();
    }

    @Override
    public String getBotPath() {
        return telegramBotConfig.getBotEndpoint();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Interceptor interceptor = interceptorFactory.getInterceptor(update);
        BotApiMethod<?> method = interceptor.intercept(update);
        if (interceptor.getType() == InterceptorTypes.MESSAGE_ENTITY)
            addMenuCommands(update.getMessage());
        executeMethod(method, interceptor.getTelegramUserId(), interceptor.getChatId());
        return null;
    }

    private void executeMethod(BotApiMethod<?> method, Long telegramUserId, Long chatId) {
        log.info("Execute method for user with id : {} and chat id : {}", telegramUserId, chatId);
        try {
            Serializable executed = execute(method);
            if (executed instanceof Message message) {
                deletePreviousMessage(telegramUserId, chatId);
                botMessageService.save(telegramUserId, message);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletePreviousMessage(Long telegramUserId, Long chatId) {
        log.info("Delete message from user : {} and chat id : {}",
                telegramUserId, chatId);
        List<BotMessage> previousMessages = botMessageService.findAllByTelegramIdAndChatId(telegramUserId, chatId);
        if (previousMessages != null && !previousMessages.isEmpty())
            previousMessages.forEach(previousMessage -> {
                botMessageService.delete(previousMessage);
                DeleteMessage deleteMessage = DeleteMessage.builder()
                        .chatId(previousMessage.getChatId().toString())
                        .messageId(previousMessage.getMessageId())
                        .build();
                try {
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    log.error(e.getMessage());
                    correctLongLiveMessage(previousMessage);
                }
            });
    }


    public void deleteExpiredMessage(BotMessage botMessage) {
        log.info("Delete expired message with message id : {} and chat id : {}",
                botMessage.getMessageId(), botMessage.getChatId());
        try {
            execute(DeleteMessage.builder()
                    .messageId(botMessage.getMessageId())
                    .chatId(botMessage.getChatId().toString())
                    .build());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            correctLongLiveMessage(botMessage);
        }
    }

    private void correctLongLiveMessage(BotMessage previousMessage) {
        log.info("Correct long live message with id : {} and chat id : {}", previousMessage.getMessageId(), previousMessage.getChatId());
        try {
            execute(EditMessageText.builder()
                    .messageId(previousMessage.getMessageId())
                    .chatId(previousMessage.getChatId().toString())
                    .text("Message deleted")
                    .build());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw new DeleteMessageException(previousMessage);
        }
    }

    private void addMenuCommands(Message message) {
        log.info("Add bot menu commands for user");
        try {
            execute(new SetMyCommands(BotMenuUtils.getCommands(),
                    new BotCommandScopeChat(message.getChat().getId().toString()), null));
        } catch (TelegramApiException e) {
            log.error("Can't add commands. We will try next time");
        }
    }
}
