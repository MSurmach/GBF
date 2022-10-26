package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Component
public class EmptyKeyboardType implements KeyboardType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        return null;
    }
}
