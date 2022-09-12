package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitStateHandler implements BotStateHandler {

    private LocaleMessageSource localeMessageSource;

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Override
    public SendMessage handleUpdate(Update update) {
        collectData(update);
        SendMessage message = new SendMessage();
        message.setText(textAnswer(update.getMessage().getFrom().getUserName()));
        message.setReplyMarkup(inlineKeyboardMarkup());
        message.setChatId(update.getMessage().getChatId().toString());
        return message;
    }

    private void collectData(Update update) {
        User user = update.getMessage().getFrom();
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setUsername(user.getUserName());
        UserDataCache.addToCache(userData.getId(), userData);
    }

    private String textAnswer(String username) {
        return localeMessageSource.getLocaleMessage("start.message", username);
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        var courierButton = new InlineKeyboardButton();
        courierButton.setText(localeMessageSource.getLocaleMessage("start.button.courier"));
        courierButton.setCallbackData("COURIER");
        buttons.add(courierButton);

        var customerButton = new InlineKeyboardButton();
        customerButton.setText(localeMessageSource.getLocaleMessage("start.button.customer"));
        customerButton.setCallbackData("CUSTOMER");
        buttons.add(customerButton);

        var registrationsButton = new InlineKeyboardButton();
        registrationsButton.setText(localeMessageSource.getLocaleMessage("start.button.registrations"));
        registrationsButton.setCallbackData("REGISTRATIONS");
        buttons.add(registrationsButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        buttons.forEach(button -> keyboard.add(List.of(button)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
