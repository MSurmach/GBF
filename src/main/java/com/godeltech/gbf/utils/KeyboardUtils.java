package com.godeltech.gbf.utils;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.button.BotButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class KeyboardUtils {

    private final static String IGNORE = "IGNORE";

    public static InlineKeyboardButton createLocalButton(String label, String callBack, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(label));
        button.setCallbackData(callBack);
        return button;
    }

    public static InlineKeyboardButton createButton(String label, String callBack) {
        var button = new InlineKeyboardButton(label);
        button.setCallbackData(callBack);
        return button;
    }

    public static InlineKeyboardButton createButtonWithData(String label, BotButton botButton, String data) {
        var button = new InlineKeyboardButton(label);
        String callback = botButton + ":" + data;
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createLocalButtonWithData(BotButton botButton, String data, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(botButton.name()));
        String callback = botButton + ":" + data;
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createLocalButton(BotButton botButton, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(botButton.name()));
        button.setCallbackData(botButton.name());
        return button;
    }

    public static InlineKeyboardButton createLocalButton(String label, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(label));
        button.setCallbackData(IGNORE);
        return button;
    }

    public static InlineKeyboardButton createButton() {
        var button = new InlineKeyboardButton(" ");
        button.setCallbackData(IGNORE);
        return button;
    }
}
