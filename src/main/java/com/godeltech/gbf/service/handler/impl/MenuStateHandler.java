package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.MenuKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class MenuStateHandler extends LocaleBotStateHandler {

    private Keyboard keyboard;

    public MenuStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setMenuKeyboard(MenuKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public void handleUpdate(Update update) {
        User telegramUser = update.getMessage().getFrom();
        UserData userData = new UserData();
        userData.setId(telegramUser.getId());
        userData.setUsername(telegramUser.getUserName());
        UserDataCache.addToCache(userData.getId(), userData);
    }

    @Override
    public SendMessage getView(Update update) {
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        SendMessage message = new SendMessage();
        message.setText(textAnswer(username));
        message.setReplyMarkup(keyboard.getKeyboardMarkup());
        message.setChatId(chatId);
        return message;
    }

    private String textAnswer(String username) {
        return localeMessageSource.getLocaleMessage("start.message", username);
    }
}
