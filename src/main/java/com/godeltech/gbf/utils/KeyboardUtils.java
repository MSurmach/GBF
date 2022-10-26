package com.godeltech.gbf.utils;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.utils.ButtonUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CommonButton.CONFIRM;
import static com.godeltech.gbf.gui.button.NavigationBotButton.GLOBAL_BACK;
import static com.godeltech.gbf.gui.button.NavigationBotButton.MENU;
import static com.godeltech.gbf.utils.ButtonUtils.createLocalButton;

public class KeyboardUtils {

    public static InlineKeyboardMarkup backAndMenuMarkup(LocalMessageSource lms) {
        var backButton = createLocalButton(GLOBAL_BACK, lms);
        var menuButton = createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(backButton, menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup menuMarkup(LocalMessageSource lms) {
        var menuButton = ButtonUtils.createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup confirmMarkup(LocalMessageSource lms) {
        var confirmButton = createLocalButton(CONFIRM, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(confirmButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}

