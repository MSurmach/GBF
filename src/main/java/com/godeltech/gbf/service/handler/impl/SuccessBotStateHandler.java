package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class SuccessBotStateHandler extends LocaleBotStateHandler {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public SuccessBotStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setText(textAnswer());
        sendMessage.setReplyMarkup(controlKeyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer() {
        return localeMessageSource.getLocaleMessage("courier.registration.success");
    }
}
