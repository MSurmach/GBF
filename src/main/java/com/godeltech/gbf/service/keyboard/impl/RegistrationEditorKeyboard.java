package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.RegistrationEditorBotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.utils.KeyboardUtils;
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
        RegistrationEditorBotButton[] botButtons = RegistrationEditorBotButton.values();
        List<List<InlineKeyboardButton>> keyboard = Arrays.stream(botButtons).
                map(botButton -> List.of(buildButton(botButton))).
                toList();
        return new InlineKeyboardMarkup(keyboard);
    }

    private InlineKeyboardButton buildButton(RegistrationEditorBotButton button) {
        String label = button.localLabel(localMessageSource);
        String callback = button.name();
        return KeyboardUtils.createButton(label, callback);
    }
}
