package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.Load;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

@Service
public class LoadKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public LoadKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        Load[] loads = Load.values();
        List<InlineKeyboardButton> buttons = Arrays.stream(loads).
                map(load -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(load.name());
                    button.setCallbackData(load.name());
                    return button;
                }).toList();
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(List.of(buttons));
        return new KeyboardAppender(loadKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
    }
}
