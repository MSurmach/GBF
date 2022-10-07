package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.RegistrationBotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.RegistrationBotButton.*;

@Service
@AllArgsConstructor
public class RegistrationRecordKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        Long recordId = userData.getRecordId();
        var editButton = buildButton(REGISTRATION_EDIT, recordId);
        var deleteButton = buildButton(REGISTRATION_DELETE, recordId);
        var findButton = buildButton(REGISTRATION_FIND_CLIENTS, recordId);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(editButton, deleteButton, findButton));
        return new InlineKeyboardMarkup(keyboard);
    }

    private InlineKeyboardButton buildButton(RegistrationBotButton button, Long recordId) {
        String label = button.localLabel(localMessageSource);
        String callback = button.name() + ":" + recordId;
        return KeyboardUtils.createButton(label, callback);
    }
}
