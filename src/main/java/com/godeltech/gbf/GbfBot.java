package com.godeltech.gbf;

import com.godeltech.gbf.interceptors.Interceptor;
import com.godeltech.gbf.interceptors.InterceptorFactory;
import com.godeltech.gbf.interceptors.ReplyInterceptor;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GbfBot extends TelegramWebhookBot {

    private final String botUsername;

    private final String botToken;

    private final String botPath;

    private final ReplyInterceptor replyInterceptor;

    public GbfBot(String botUsername, String botToken, String botPath, ReplyInterceptor replyInterceptor) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
        this.replyInterceptor = replyInterceptor;
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
        Interceptor interceptor = InterceptorFactory.getInstance().getInterceptor(update);
        return interceptor.intercept(update);
    }
}
