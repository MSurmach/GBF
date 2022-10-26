package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
public class CommentKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        return backAndMenuMarkup(lms);
    }
}

