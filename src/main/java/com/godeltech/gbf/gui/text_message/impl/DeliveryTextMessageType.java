package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

@Component
@AllArgsConstructor
@Slf4j
public class DeliveryTextMessageType implements TextMessageType {

    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DELIVERY;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create delivery message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        String question = switch (sessionData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(DELIVERY_COURIER_QUESTION_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(DELIVERY_CLIENT_QUESTION_CODE);
        };
        String note = Objects.isNull(sessionData.getDelivery())?
                EMPTY:
                lms.getLocaleMessage(DELIVERY_NOTE_CODE);
        return lms.getLocaleMessage(DELIVERY_DESCRIPTION_CODE) + question + note;
    }
}
