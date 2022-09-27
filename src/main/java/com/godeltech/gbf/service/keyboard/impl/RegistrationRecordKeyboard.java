package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.BotButton.Registration.REGISTRATION_DELETE;
import static com.godeltech.gbf.management.button.BotButton.Registration.REGISTRATION_EDIT;

@Service
public class RegistrationRecordKeyboard implements Keyboard {
    private BackMenuKeyboard backMenuKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var editButton = buildButton(REGISTRATION_EDIT);
        var deleteButton = buildButton(REGISTRATION_DELETE);
        List<InlineKeyboardButton> row = List.of(editButton, deleteButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(backMenuKeyboard.getKeyboardMarkup(userData)).result();
    }

    private InlineKeyboardButton buildButton(BotButton.Registration button) {
        String label = button.getLocalMessage(localMessageSource);
        String callback = button.name();
        return KeyboardUtils.createButton(label, callback);
    }
}
