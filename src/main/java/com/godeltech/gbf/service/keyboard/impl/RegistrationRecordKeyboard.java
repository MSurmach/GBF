package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.RegistrationBotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.RegistrationBotButton.REGISTRATION_DELETE;
import static com.godeltech.gbf.management.button.RegistrationBotButton.REGISTRATION_EDIT;

@Service
@AllArgsConstructor
public class RegistrationRecordKeyboard implements Keyboard {
    private BackMenuKeyboard backMenuKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        Long recordId = userData.getId();
        var editButton = buildButton(REGISTRATION_EDIT, recordId);
        var deleteButton = buildButton(REGISTRATION_DELETE, recordId);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(editButton, deleteButton));
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(backMenuKeyboard.getKeyboardMarkup(userData)).result();
    }

    private InlineKeyboardButton buildButton(RegistrationBotButton button, Long recordId) {
        String label = button.localLabel(localMessageSource);
        String callback = button.name() + ":" + recordId;
        return KeyboardUtils.createButton(label, callback);
    }
}
