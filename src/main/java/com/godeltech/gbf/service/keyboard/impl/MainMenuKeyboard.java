package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.management.button.MainMenuButton.*;

@Service
@AllArgsConstructor
public class MainMenuKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String courierLabel = COURIER.localLabel(localMessageSource);
        String courierCallback = COURIER.name();
        var courierButton = KeyboardUtils.createButton(courierLabel, courierCallback);

        String customerLabel = CUSTOMER.localLabel(localMessageSource);
        String customerCallback = CUSTOMER.name();
        var customerButton = KeyboardUtils.createButton(customerLabel, customerCallback);

        String registrationsCourierLabel = REGISTRATIONS_VIEWER.localLabel(localMessageSource);
        String registrationsCourierCallback = REGISTRATIONS_VIEWER.name();
        var registrationsButton = KeyboardUtils.createButton(registrationsCourierLabel, registrationsCourierCallback);

        List<InlineKeyboardButton> buttons = List.of(courierButton, customerButton, registrationsButton);
        List<List<InlineKeyboardButton>> keyboard = buttons.stream().map(List::of).toList();
        return new InlineKeyboardMarkup(keyboard);
    }
}
