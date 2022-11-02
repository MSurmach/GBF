package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.RequestButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class RequestKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REQUESTS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        String recordId = String.valueOf(sessionData.getOfferId());
        var editButton = createLocalButtonWithData(REQUEST_EDIT, recordId, lms);
        var deleteButton = createLocalButtonWithData(REQUEST_DELETE, recordId, lms);
        var findButton = createLocalButtonWithData(REQUEST_FIND_COURIERS, recordId, lms);
        List<List<InlineKeyboardButton>> requestKeyboard = new ArrayList<>();
        requestKeyboard.add(List.of(editButton, deleteButton, findButton));
        return new InlineKeyboardMarkup(requestKeyboard);
    }
}
