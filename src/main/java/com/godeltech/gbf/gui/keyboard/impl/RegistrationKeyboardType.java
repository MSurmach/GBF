package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.utils.KeyboardUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.RegistrationBotButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class RegistrationKeyboardType implements KeyboardType {
    private final LocalMessageSource lms;
    private final PaginationKeyboardType paginationKeyboardType;

    @Override
    public State getState() {
        return State.REGISTRATIONS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        if (page == null || page.isEmpty()) return KeyboardUtils.menuMarkup(lms);
        String offerId = page.getContent().get(0).getId().toString();
        var editButton = createLocalButtonWithData(REGISTRATION_EDIT, offerId, lms);
        var deleteButton = createLocalButtonWithData(REGISTRATION_DELETE, offerId, lms);
        var findButton = createLocalButtonWithData(REGISTRATION_FIND_CLIENTS, offerId, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(editButton, deleteButton, findButton));
        var keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).
                append(paginationKeyboardType.getKeyboardMarkup(sessionData)).
                append(KeyboardUtils.menuMarkup(lms)).
                result();
    }
}
