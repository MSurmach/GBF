package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.management.button.BotButton.*;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class MenuKeyboard extends LocaleKeyboard {

    @Autowired
    public MenuKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String courierLabel = COURIER.getLocalMessage(localMessageSource);
        String courierCallback = COURIER.name();
        var courierButton = KeyboardUtils.createButton(courierLabel, courierCallback);

        String customerLabel = CUSTOMER.getLocalMessage(localMessageSource);
        String customerCallback = CUSTOMER.name();
        var customerButton = KeyboardUtils.createButton(customerLabel, customerCallback);

        String registrationsCourierLabel = REGISTRATIONS.getLocalMessage(localMessageSource);
        String registrationsCourierCallback = REGISTRATIONS.name();
        var registrationsButton = KeyboardUtils.createButton(registrationsCourierLabel, registrationsCourierCallback);

        List<InlineKeyboardButton> buttons = List.of(courierButton, customerButton, registrationsButton);
        List<List<InlineKeyboardButton>> keyboard = buttons.stream().map(List::of).toList();
        return new InlineKeyboardMarkup(keyboard);
    }
}
