package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CommonButton.CONFIRM;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButton;

@Component
@AllArgsConstructor
public class ConfirmKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String confirmLabel = CONFIRM.localLabel(localMessageSource);
        String confirmCallback = CONFIRM.name();
        var confirmButton = KeyboardUtils.createLocalButton(confirmLabel, confirmCallback);
        List<InlineKeyboardButton> row = List.of(confirmButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup confirmMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(confirmMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
