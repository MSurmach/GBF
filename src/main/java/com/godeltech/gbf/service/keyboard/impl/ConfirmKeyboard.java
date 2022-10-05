package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.CommonButton.CONFIRM;
import static com.godeltech.gbf.utils.KeyboardUtils.createButton;

@Service
@AllArgsConstructor
public class ConfirmKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String confirmLabel = CONFIRM.localLabel(localMessageSource);
        String confirmCallback = CONFIRM.name();
        var confirmButton = createButton(confirmLabel, confirmCallback);
        List<InlineKeyboardButton> row = List.of(confirmButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup confirmMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(confirmMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
