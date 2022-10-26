package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.gui.keyboard.ControlKeyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.CargoSize;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CargoPackageKeyboardType implements KeyboardType {

    private ControlKeyboard controlKeyboard;

    @Override
    public State getState() {
        return State.CARGO_PACKAGE;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<InlineKeyboardButton> row = CargoSize.getEntries().stream().
                map(entry -> KeyboardUtils.createButton(entry.getValue(), entry.getKey().toString())).
                toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        var packageKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(packageKeyboardMarkup).append(controlKeyboard.controlMarkup()).result();
    }
}
