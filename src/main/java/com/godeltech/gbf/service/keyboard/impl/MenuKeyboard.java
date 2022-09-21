package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.StateFlow;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class MenuKeyboard extends LocaleKeyboard {

    @Autowired
    public MenuKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String courierLabel = localeMessageSource.getLocaleMessage("start.button.courier");
        String courierCallback = StateFlow.COURIER.name();
        var courierButton = createButton(courierLabel, courierCallback);

        String customerLabel = localeMessageSource.getLocaleMessage("start.button.customer");
        String customerCallback = StateFlow.CUSTOMER.name();
        var customerButton = createButton(customerLabel, customerCallback);

        String registrationsCourierLabel = localeMessageSource.getLocaleMessage("start.button.registrations");
        String registrationsCourierCallback = StateFlow.VIEWER.name();
        var registrationsButton = createButton(registrationsCourierLabel, registrationsCourierCallback);

        List<InlineKeyboardButton> buttons = List.of(courierButton, customerButton, registrationsButton);
        List<List<InlineKeyboardButton>> keyboard = buttons.stream().map(List::of).toList();
        return new InlineKeyboardMarkup(keyboard);
    }
}
