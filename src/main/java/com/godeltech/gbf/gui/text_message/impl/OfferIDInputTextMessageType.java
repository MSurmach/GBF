package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;

@Component
@AllArgsConstructor
public class OfferIDInputTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.OFFER_ID_INPUT;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        String infoMessage = lms.getLocaleMessage(OFFER_ID_INPUT_INFO_CODE);
        String questionMessage = Objects.equals(session.getRole(), REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(OFFER_ID_INPUT_REGISTRATION_QUESTION_CODE) :
                lms.getLocaleMessage(OFFER_ID_INPUT_REQUEST_QUESTION_CODE);
        return infoMessage + questionMessage;
    }
}
