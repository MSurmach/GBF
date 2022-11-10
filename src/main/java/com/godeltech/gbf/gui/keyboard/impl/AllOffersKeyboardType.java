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

import static com.godeltech.gbf.model.State.ALL_OFFERS;

@Component
@AllArgsConstructor
public class AllOffersKeyboardType implements KeyboardType {
    private final LocalMessageSource lms;
    private final PaginationKeyboardType paginationKeyboardType;

    @Override
    public State getState() {
        return ALL_OFFERS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        Page<Offer> page = sessionData.getOffers();
        if (page == null || page.isEmpty()) return KeyboardUtils.menuMarkup(lms);
        return new KeyboardMarkupAppender().
                append(paginationKeyboardType.getKeyboardMarkup(sessionData)).
                append(KeyboardUtils.menuMarkup(lms)).
                result();
    }
}