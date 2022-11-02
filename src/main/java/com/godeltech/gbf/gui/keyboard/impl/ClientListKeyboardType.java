package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Component
public class ClientListKeyboardType implements KeyboardType {
    @Override
    public State getState() {
        return State.CLIENTS_LIST_RESULT;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        return null;
    }
}
