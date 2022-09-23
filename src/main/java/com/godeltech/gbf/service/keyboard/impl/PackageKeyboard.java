package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class PackageKeyboard extends LocaleKeyboard {

    private final Keyboard controlKeyboard;

    public PackageKeyboard(LocalMessageSource localMessageSource, ControlKeyboard controlKeyboard) {
        super(localMessageSource);
        this.controlKeyboard = controlKeyboard;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String[] packageSizes = {"S", "M", "L"};
        List<InlineKeyboardButton> row = Arrays.stream(packageSizes).
                map(packageSize -> createButton(packageSize, packageSize)).
                toList();
        var packageKeyboardMarkup = new InlineKeyboardMarkup(List.of(row));
        return new KeyboardMarkupAppender(packageKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }
}
