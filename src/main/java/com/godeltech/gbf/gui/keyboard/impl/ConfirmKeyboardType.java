package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
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
public class ConfirmKeyboardType implements KeyboardType {
    private LocalMessageSource lms;
    private ControlKeyboardType controlKeyboard;

    @Override
    public State getState() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        var confirmButton = createLocalButton(CONFIRM, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(confirmButton));
        return new KeyboardMarkupAppender(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }
}
