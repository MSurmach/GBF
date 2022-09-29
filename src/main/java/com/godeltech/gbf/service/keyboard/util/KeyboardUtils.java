package com.godeltech.gbf.service.keyboard.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class KeyboardUtils {

    private final static String ALERT = "ALERT";

    public static InlineKeyboardButton createButton(String label, String callBack) {
        var button = new InlineKeyboardButton(label);
        button.setCallbackData(callBack);
        return button;
    }

    public static InlineKeyboardButton createButton(String label) {
        var button = new InlineKeyboardButton(label);
        button.setCallbackData(ALERT);
        return button;
    }

    public static InlineKeyboardButton createButton() {
        var button = new InlineKeyboardButton(" ");
        button.setCallbackData(ALERT);
        return button;
    }
}
