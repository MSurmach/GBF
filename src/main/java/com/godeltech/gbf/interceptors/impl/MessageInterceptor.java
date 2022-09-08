package com.godeltech.gbf.interceptors.impl;

import com.godeltech.gbf.interceptors.Interceptor;
import com.godeltech.gbf.keyboard.impl.RoleKeyboard;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageInterceptor implements Interceptor {
    @Override
    public BotApiMethod<?> intercept(Update update) {
        RoleKeyboard roleKeyboard = new RoleKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Answer");
        sendMessage.setReplyMarkup(roleKeyboard.getKeyBoardMarkup());
        return sendMessage;
    }
}
