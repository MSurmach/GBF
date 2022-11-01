package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.godeltech.gbf.gui.button.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.keyboard.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
public class DeliveryKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DELIVERY;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        InlineKeyboardMarkup loadKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(loadKeyboardMarkup).
                append(backAndMenuMarkup(lms)).
                result();
    }
}
