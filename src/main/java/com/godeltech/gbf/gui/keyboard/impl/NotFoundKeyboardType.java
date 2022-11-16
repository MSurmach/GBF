package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
@Slf4j
public class NotFoundKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;
    @Override
    public State getState() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        log.debug("Create NotFound keyboard type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername());
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        return backAndMenuMarkup(lms);
    }
}
