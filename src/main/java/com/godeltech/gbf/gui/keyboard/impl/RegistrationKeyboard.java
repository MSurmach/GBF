package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.RegistrationBotButton.*;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class RegistrationKeyboard implements Keyboard {
    private LocalMessageSource lms;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String recordId = String.valueOf(userData.getUserId());
        var editButton = createLocalButtonWithData(REGISTRATION_EDIT, recordId, lms);
        var deleteButton = createLocalButtonWithData(REGISTRATION_DELETE, recordId, lms);
        var findButton = createLocalButtonWithData(REGISTRATION_FIND_CLIENTS, recordId, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(editButton, deleteButton, findButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
