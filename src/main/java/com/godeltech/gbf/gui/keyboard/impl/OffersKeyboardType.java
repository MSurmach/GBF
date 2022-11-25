package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.utils.KeyboardUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.OffersBotButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class OffersKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;
    private final PaginationKeyboardType paginationKeyboardType;

    @Override
    public State getState() {
        return State.MY_OFFERS;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        Page<Offer> page = session.getOffers();
        if (page == null || page.isEmpty()) return KeyboardUtils.menuMarkup(lms);
        String offerId = page.getContent().get(0).getId().toString();
        var editButton = createLocalButtonWithData(OFFER_EDIT, offerId, lms);
        var deleteButton = createLocalButtonWithData(OFFER_DELETE, offerId, lms);
        var findByIdButton = createLocalButton(OFFER_FIND_BY_ID, lms);
        var findButton = session.getRole() == Role.REGISTRATIONS_VIEWER ?
                createLocalButtonWithData(OFFER_FIND_CLIENTS, offerId, lms) :
                createLocalButtonWithData(OFFER_FIND_COURIERS, offerId, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(findButton));
        keyboard.add(List.of(editButton, deleteButton, findByIdButton));
        var keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).
                append(paginationKeyboardType.getKeyboardMarkup(session)).
                append(KeyboardUtils.menuMarkup(lms)).
                result();
    }
}
