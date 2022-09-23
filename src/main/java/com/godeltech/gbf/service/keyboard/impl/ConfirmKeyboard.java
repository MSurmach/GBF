package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class ConfirmKeyboard extends LocaleKeyboard {
    public ConfirmKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String label = localMessageSource.getLocaleMessage("confirm");
        String buttonCallback = State.CONFIRMATION.name();
        var confirmButton = createButton(label, buttonCallback);
        List<InlineKeyboardButton> row = List.of(confirmButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return new InlineKeyboardMarkup(keyboard);
    }
}
