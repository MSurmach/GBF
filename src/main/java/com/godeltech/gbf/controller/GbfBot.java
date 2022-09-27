package com.godeltech.gbf.controller;

import com.godeltech.gbf.cache.UserMessageCache;
import com.godeltech.gbf.config.TelegramBotConfig;
import com.godeltech.gbf.service.factory.InterceptorFactory;
import com.godeltech.gbf.service.interceptor.Interceptor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GbfBot extends SpringWebhookBot {
    private final InterceptorFactory interceptorFactory;
    private final TelegramBotConfig telegramBotConfig;

    public GbfBot(DefaultBotOptions options, SetWebhook setWebhook, InterceptorFactory interceptorFactory, TelegramBotConfig telegramBotConfig) {
        super(options, setWebhook);
        this.interceptorFactory = interceptorFactory;
        this.telegramBotConfig = telegramBotConfig;
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
        Interceptor interceptor = interceptorFactory.getInterceptor(update);
        List<? extends BotApiMethod<?>> methods = interceptor.intercept(update);
        Long telegramUserId = interceptor.getUserId();
        List<Message> executedMessages = executeMethods(methods);
        deletePreviousMessage(telegramUserId, interceptor.getChatId());
        cacheExecutedMessages(executedMessages, telegramUserId);
        return null;
    }

    private List<Message> executeMethods(List<? extends BotApiMethod<?>> methods) {
        return methods.stream().map(botApiMethod -> {
            try {
                return (Message) execute(botApiMethod);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private void cacheExecutedMessages(List<Message> messages, Long telegramUserId) {
        messages.forEach(message ->
                UserMessageCache.cacheUserIdAndMessageId(telegramUserId, message.getMessageId()));
    }

    private void deletePreviousMessage(Long telegramUserId, Long chatId) {
        List<Integer> messageIds = UserMessageCache.getMessageIds(telegramUserId);
        if (messageIds != null) {
            DeleteMessage deleteMessage = new DeleteMessage();
            messageIds.forEach(messageId -> {
                deleteMessage.setChatId(chatId);
                deleteMessage.setMessageId(messageId);
                try {
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
