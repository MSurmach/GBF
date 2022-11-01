package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.exception.NotFoundStateTypeException;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Component
public class NotFoundKeyboardType implements KeyboardType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        throw new NotFoundStateTypeException(NotFoundKeyboardType.class, userData.getUsername(), userData.getId());
    }
}
