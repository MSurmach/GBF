package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.management.button.FindCourierBotButton.LOOK_AT_COURIERS;
import static com.godeltech.gbf.utils.KeyboardUtils.createButton;

@Service
@AllArgsConstructor
public class FoundCouriersInfoKeyboard implements Keyboard {
    private LocalMessageSource localMessageSource;
    private ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        Page<UserRecord> records = userData.getRecordsPage();
        if (records.isEmpty()) return controlKeyboard.getKeyboardMarkup(userData);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        String lookAtCouriersLabel = LOOK_AT_COURIERS.localLabel(localMessageSource);
        var lookButton = createButton(lookAtCouriersLabel);
        keyboard.add(List.of(lookButton));
        var keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }
}
