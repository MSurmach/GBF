package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;
import static com.godeltech.gbf.model.State.FEEDBACK;

@Component
@AllArgsConstructor
public class FeedbackKeyboardType implements KeyboardType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return FEEDBACK;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        return backAndMenuMarkup(lms);
    }
}