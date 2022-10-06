package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Service
public class EmptyKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        return null;
    }
}
