package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.NavigationBotButton.GLOBAL_BACK;
import static com.godeltech.gbf.gui.button.NavigationBotButton.MENU;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;


@Data
@AllArgsConstructor
public abstract class ControlKeyboardType{
    private LocalMessageSource lms;


    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var backButton = createLocalButton(GLOBAL_BACK, lms);
        var menuButton = createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(backButton, menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}

