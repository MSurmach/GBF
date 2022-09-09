package com.godeltech.gbf.interceptors.impl;

import com.godeltech.gbf.SendMessageBuilder;
import com.godeltech.gbf.interceptors.Interceptor;
import com.godeltech.gbf.state.BotState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageInterceptor implements Interceptor {
    private final String startCommand = "/start";
    private final String stopCommand = "/stop";

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        String input = message.getText();
        if (input.equalsIgnoreCase(startCommand)) {
            Long chatId = message.getChatId();
            BotState botState = BotState.START;
            String text = "Hello, please choose your role (you want to transfer something, or you want to look at list of people, that can help you). If you want to see your registrations, please click the \"Registrations\" button";
            return new SendMessageBuilder(botState).chat_id(chatId).text(text).build();
        }
        return null;
    }
}
