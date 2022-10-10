package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class CargoPackageKeyboard implements Keyboard {

    private final ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String[] packageSizes = {"S", "M", "L"};
        List<InlineKeyboardButton> row = Arrays.stream(packageSizes).
                map(packageSize -> KeyboardUtils.createButton(packageSize, packageSize)).
                toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        var packageKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(packageKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
