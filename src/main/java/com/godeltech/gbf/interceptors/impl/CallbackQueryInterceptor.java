package com.godeltech.gbf.interceptors.impl;

import com.godeltech.gbf.interceptors.Interceptor;
import com.godeltech.gbf.keyboard.impl.CountryKeyboard;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackQueryInterceptor implements Interceptor {

    @Override
    public BotApiMethod<?> intercept(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        CountryKeyboard countryKeyboard = new CountryKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        sendMessage.setText("CallBack");
        sendMessage.setReplyMarkup(countryKeyboard.getKeyBoardMarkup());
        return sendMessage;

    }
}
