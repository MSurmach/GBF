package com.godeltech.gbf.keyboard.impl;

import com.godeltech.gbf.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class RoleKeyboard implements Keyboard {

    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        var button1 = new InlineKeyboardButton();
        button1.setText("You can transfer something.");
        button1.setCallbackData("Some callback");
        buttons.add(button1);

        var button2 = new InlineKeyboardButton();
        button2.setText("You want to get something.");
        button2.setCallbackData("Some callback");
        buttons.add(button2);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttons);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
