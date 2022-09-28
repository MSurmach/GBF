package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationEditorKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        BotButton.RegistrationEditor[] botButtons = BotButton.RegistrationEditor.values();
        List<List<InlineKeyboardButton>> keyboard = Arrays.stream(botButtons).
                map(botButton -> List.of(buildButton(botButton))).
                toList();
        return new InlineKeyboardMarkup(keyboard);
    }

    private InlineKeyboardButton buildButton(BotButton.RegistrationEditor button) {
        String label = button.getLocalMessage(localMessageSource);
        String callback = button.name();
        return KeyboardUtils.createButton(label, callback);
    }
}
