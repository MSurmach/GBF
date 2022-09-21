package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.Load;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

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
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        Load[] loads = Load.values();
        List<InlineKeyboardButton> buttons = Arrays.stream(loads).
                map(load -> {
                    String label = load.getDescription(localeMessageSource);
                    String buttonCallback = load.name();
                    return createButton(label, buttonCallback);
                }).toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttons);
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(loadKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }
}
