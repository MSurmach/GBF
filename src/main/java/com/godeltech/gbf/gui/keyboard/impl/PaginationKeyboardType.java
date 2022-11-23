package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.PaginationButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createButton;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;

@Component
@AllArgsConstructor
@Slf4j
public class PaginationKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.NON_USABLE;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        log.debug("Create pagination keyboard type for session data with user id : {} and username : {}",
                session.getTelegramUser().getId(), session.getTelegramUser().getUserName() );
        var startButton = createLocalButton(PAGE_START, lms);
        var prevButton = createLocalButton(PAGE_PREVIOUS, lms);
        var pageNumber = session.getPageNumber() + 1;
        var pageCount = session.getOffers().getTotalPages();
        var pageButton = createButton(
                lms.getLocaleMessage(PAGE_CURRENT.name(), pageNumber + "/" + pageCount),
                PAGE_CURRENT.name());
        var nextButton = createLocalButton(PAGE_NEXT, lms);
        var endButton = createLocalButton(PAGE_END, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(startButton, prevButton, pageButton, nextButton, endButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
