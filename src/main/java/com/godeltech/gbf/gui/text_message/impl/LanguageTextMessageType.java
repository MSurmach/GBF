package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.LANGUAGE_QUESTION_CODE;

@Component
@AllArgsConstructor
public class LanguageTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.LANGUAGE;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        return lms.getLocaleMessage(LANGUAGE_QUESTION_CODE);
    }
}
