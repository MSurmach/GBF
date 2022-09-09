package com.godeltech.gbf.keyboard.impl;

import com.godeltech.gbf.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartKeyboard implements Keyboard {

    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        var courierButton = new InlineKeyboardButton();
        courierButton.setText("You can transfer something");
        courierButton.setCallbackData("COURIER");
        buttons.add(courierButton);

        var customerButton = new InlineKeyboardButton();
        customerButton.setText("You want to get something");
        customerButton.setCallbackData("CUSTOMER");
        buttons.add(customerButton);

        var registrationsButton = new InlineKeyboardButton();
        registrationsButton.setText("Registrations");
        registrationsButton.setCallbackData("REGISTRATIONS");
        buttons.add(registrationsButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        buttons.forEach(button -> keyboard.add(List.of(button)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
