package com.godeltech.gbf.bot;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

public class GbfBot extends SpringWebhookBot {
    private final String botUsername;
    private final String botToken;
    private final String botPath;


    public GbfBot(SetWebhook setWebhook, String botUsername, String botToken, String botPath) {
        super(setWebhook);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
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
        return null;
    }
}
