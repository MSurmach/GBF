package com.godeltech.gbf.controller;

import com.godeltech.gbf.config.TelegramBotConfig;
import com.godeltech.gbf.service.factory.InterceptorFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

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
        BotApiMethod<?> intercepted = interceptorFactory.intercept(update);
        deletePreviousMessage(update);
        return intercepted;
    }

    private void deletePreviousMessage(Update update) {
        if (update.hasCallbackQuery()) {
            DeleteMessage deleteMessage = new DeleteMessage();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            deleteMessage.setChatId(chatId);
            deleteMessage.setMessageId(messageId);
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
