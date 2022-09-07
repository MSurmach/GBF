package com.godeltech.gbf.bot;

import com.godeltech.gbf.view.RoleView;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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
        RoleView roleView = new RoleView();
        SendMessage message = new SendMessage();
        message.setText("Please select your role:");
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyMarkup(roleView.getView());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
