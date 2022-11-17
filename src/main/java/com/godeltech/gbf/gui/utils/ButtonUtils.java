package com.godeltech.gbf.gui.utils;

import com.godeltech.gbf.gui.button.BotButton;
import com.godeltech.gbf.localization.LocalMessageSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

public class ButtonUtils {

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

    public static InlineKeyboardButton createButton(String label, BotButton botButton) {
        var button = new InlineKeyboardButton(label);
        button.setCallbackData(botButton.name());
        return button;
    }

    public static InlineKeyboardButton createButtonWithData(String label, BotButton botButton, String data) {
        var button = new InlineKeyboardButton(label);
        String callback = botButton + SPLITTER + data;
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createButton(BotButton botButton) {
        var button = new InlineKeyboardButton(botButton.name());
        String callback = botButton.name();
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createLocalButtonWithData(String label, BotButton botButton, String data, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(label));
        String callback = botButton + SPLITTER + data;
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createLocalButtonWithData(String preLabelCode, String label, BotButton botButton, String data, LocalMessageSource lms) {
        String localLabel = lms.getLocaleMessage(label);
        var button = preLabelCode == null ?
                new InlineKeyboardButton(localLabel) :
                new InlineKeyboardButton(preLabelCode + SPACE + localLabel);
        String callback = botButton + SPLITTER + data;
        button.setCallbackData(callback);
        return button;
    }

    public static InlineKeyboardButton createLocalButtonWithData(String label, String callback, String data, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(label));
        String buttonCallback = callback + SPLITTER + data;
        button.setCallbackData(buttonCallback);
        return button;
    }

    public static InlineKeyboardButton createLocalButtonWithData(BotButton botButton, String data, LocalMessageSource lms) {
        var button = new InlineKeyboardButton(lms.getLocaleMessage(botButton.name()));
        String callback = botButton + SPLITTER + data;
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
