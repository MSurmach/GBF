package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.gui.keyboard.ControlKeyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@AllArgsConstructor
public class CargoPeopleKeyboardType implements KeyboardType {
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        return controlKeyboard.controlMarkup();
    }

    @Override
    public State getState() {
        return State.CARGO_PEOPLE;
    }
}

