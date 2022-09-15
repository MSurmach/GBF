package com.godeltech.gbf;

import com.godeltech.gbf.service.factory.InterceptorFactory;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class GbfBot extends TelegramWebhookBot {

    private final String botUsername;

    private final String botToken;

    private final String botPath;

    private final InterceptorFactory interceptorFactory;

    public GbfBot(String botUsername, String botToken, String botPath, InterceptorFactory interceptorFactory) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
        this.interceptorFactory = interceptorFactory;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        BotApiMethod<?> intercepted = interceptorFactory.intercept(update);
        //deleteMessage(update);
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
