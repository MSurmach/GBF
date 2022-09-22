package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Cargo;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class CargoKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public CargoKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        return getKeyboardMarkupForInitState();
    }

    private InlineKeyboardMarkup getKeyboardMarkupForInitState() {
        Cargo[] loadCategories = Cargo.values();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (Cargo cargo : loadCategories) {
            String label = cargo.getLocalDescription(localMessageSource);
            String buttonCallback = cargo.name();
            var button = createButton(label, buttonCallback);
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(loadKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }


}
