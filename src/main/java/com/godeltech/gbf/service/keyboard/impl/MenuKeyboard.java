package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class MenuKeyboard extends LocaleKeyboard {

    @Autowired
    public MenuKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        var courierButton = new InlineKeyboardButton();
        courierButton.setText(localeMessageSource.getLocaleMessage("start.button.courier"));
        courierButton.setCallbackData(BotStateFlow.COURIER.name());

        var customerButton = new InlineKeyboardButton();
        customerButton.setText(localeMessageSource.getLocaleMessage("start.button.customer"));
        customerButton.setCallbackData(BotStateFlow.CUSTOMER.name());

        var registrationsButton = new InlineKeyboardButton();
        registrationsButton.setText(localeMessageSource.getLocaleMessage("start.button.registrations"));
        registrationsButton.setCallbackData(BotStateFlow.VIEWER.name());

        List<InlineKeyboardButton> buttons = List.of(courierButton, customerButton, registrationsButton);
        List<List<InlineKeyboardButton>> keyboard = buttons.stream().map(List::of).toList();
        return new InlineKeyboardMarkup(keyboard);
    }
}
