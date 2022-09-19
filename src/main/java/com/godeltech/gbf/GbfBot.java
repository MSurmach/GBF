package com.godeltech.gbf;

import com.godeltech.gbf.config.TelegramBotConfig;
import com.godeltech.gbf.service.factory.InterceptorFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

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
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        BotApiMethod<?> intercepted = interceptorFactory.intercept(update);
        deleteMessage(update);
        return intercepted;
    }

    private void deleteMessage(Update update) {
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
