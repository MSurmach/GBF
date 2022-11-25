package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.godeltech.gbf.gui.utils.KeyboardUtils.menuMarkup;

@Component
@AllArgsConstructor
public class SuccessRegistrationKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.SUCCESS_REGISTRATION;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        return menuMarkup(localMessageSourceFactory.get(session.getTelegramUser().getLanguage()));
    }
}
