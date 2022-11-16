package com.godeltech.gbf.gui.utils;

import com.godeltech.gbf.localization.LocalMessageSource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.NavigationBotButton.BACK;
import static com.godeltech.gbf.gui.button.NavigationBotButton.MENU;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;

public class KeyboardUtils {

    public static InlineKeyboardMarkup backAndMenuMarkup(LocalMessageSource lms) {
        var backButton = createLocalButton(BACK, lms);
        var menuButton = createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(backButton, menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup menuMarkup(LocalMessageSource lms) {
        var menuButton = createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}

