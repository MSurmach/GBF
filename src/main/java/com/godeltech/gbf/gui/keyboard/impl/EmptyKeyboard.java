package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Component
public class EmptyKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        return null;
    }
}
