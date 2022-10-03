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
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.Serializable;
import java.util.List;

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
        executeMethod(methods, interceptor.getTelegramUserId(), interceptor.getChatId());
        return null;
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
                    cacheExecutedMessage(telegramUserId, message);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void cacheExecutedMessage(Long telegramUserId, Message message) {
        UserMessageCache.cacheUserIdAndMessageId(telegramUserId, message.getMessageId());
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
