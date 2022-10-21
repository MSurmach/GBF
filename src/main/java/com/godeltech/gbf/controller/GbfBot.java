package com.godeltech.gbf.controller;

import com.godeltech.gbf.config.TelegramBotConfig;
import com.godeltech.gbf.factory.impl.InterceptorFactory;
import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.Serializable;
import java.util.List;

@RestController
public class GbfBot extends SpringWebhookBot {
    @Value("${bot.chmokiId}")
    private String chmokiId;
    private final InterceptorFactory interceptorFactory;
    private final TelegramBotConfig telegramBotConfig;

    private final BotMessageService botMessageService;

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
    @PostMapping("/callback/${telegram.bot.endpoint}")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        if (authorizeUpdate(update)) {
            Interceptor interceptor = interceptorFactory.getInterceptor(update);
            List<? extends BotApiMethod<?>> methods = interceptor.intercept(update);
            executeMethod(methods, interceptor.getTelegramUserId(), interceptor.getChatId());
        }
        return null;
    }

    private boolean authorizeUpdate(Update update) {
        Message message = null;
        if (update.hasMessage()) {
            message = update.getMessage();
        }
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
        }
        return message != null && checkIfBotCanHandleMessage(message, message.getFrom().getId());
    }

    private boolean checkIfBotCanHandleMessage(Message message, Long userId) {
        String chatId = message.getChatId().toString();
        if (chatId.equals(chmokiId)) return false;
        try {
            ChatMember chatMember = execute(GetChatMember.builder()
                    .chatId(chmokiId)
                    .userId(userId)
                    .build());
            if (chatMember instanceof ChatMemberBanned || chatMember instanceof ChatMemberLeft) return true;
        } catch (TelegramApiException e) {
            return false;
        }
        return false;
    }

    private void executeMethod(List<? extends BotApiMethod<?>> methods, Long telegramUserId, Long chatId) {
        int stage = 0;
        for (BotApiMethod<?> method : methods) {
            try {
                Serializable executed = execute(method);
                if (executed instanceof Message message) {
                    if (stage == 0) {
                        stage++;
                        deletePreviousMessage(telegramUserId, chatId);
                    }
                    botMessageService.save(telegramUserId, message);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deletePreviousMessage(Long telegramUserId, Long chatId) {
        List<BotMessage> previousMessages = botMessageService.findAllByTelegramIdAndChatId(telegramUserId, chatId);
        if (previousMessages != null && !previousMessages.isEmpty())
            previousMessages.forEach(previousMessage -> {
                botMessageService.delete(previousMessage);
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(previousMessage.getChatId());
                deleteMessage.setMessageId(previousMessage.getMessageId());
                try {
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
