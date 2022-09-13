package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotStateFlow;
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
    public void handleUpdate(Update update) {
        User telegramUser = update.getMessage().getFrom();
        UserData userData = new UserData();
        userData.setId(telegramUser.getId());
        userData.setUsername(telegramUser.getUserName());
        UserDataCache.addToCache(userData.getId(), userData);
    }

    @Override
    public SendMessage getView(Update update){
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        SendMessage message = new SendMessage();
        message.setText(textAnswer(username));
        message.setReplyMarkup(inlineKeyboardMarkup());
        message.setChatId(chatId);
        return message;
    }

    private String textAnswer(String username) {
        return localeMessageSource.getLocaleMessage("start.message", username);
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        var courierButton = new InlineKeyboardButton();
        courierButton.setText(localeMessageSource.getLocaleMessage("start.button.courier"));
        courierButton.setCallbackData(BotStateFlow.COURIER.name());
        buttons.add(courierButton);

        var customerButton = new InlineKeyboardButton();
        customerButton.setText(localeMessageSource.getLocaleMessage("start.button.customer"));
        customerButton.setCallbackData(BotStateFlow.CUSTOMER.name());
        buttons.add(customerButton);

        var registrationsButton = new InlineKeyboardButton();
        registrationsButton.setText(localeMessageSource.getLocaleMessage("start.button.registrations"));
        registrationsButton.setCallbackData(BotStateFlow.VIEWER.name());
        buttons.add(registrationsButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        buttons.forEach(button -> keyboard.add(List.of(button)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
