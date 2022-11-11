package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;

@Component
@AllArgsConstructor
public class OfferIDInputMessageType implements MessageType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.OFFER_ID_INPUT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        String infoMessage = lms.getLocaleMessage(OFFER_ID_INPUT_INFO_CODE);
        String questionMessage = Objects.equals(sessionData.getRole(), REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(OFFER_ID_INPUT_REGISTRATION_QUESTION_CODE) :
                lms.getLocaleMessage(OFFER_ID_INPUT_REQUEST_QUESTION_CODE);
        return infoMessage + questionMessage;
    }
}