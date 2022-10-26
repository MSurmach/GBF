package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.NavigationBotButton.MENU;

@Component
@AllArgsConstructor
public class BackMenuKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.SUCCESS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var menuButton = KeyboardUtils.createLocalButton(MENU, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(menuButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
