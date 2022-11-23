package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
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

    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.DELIVERY;
    }

    @Override
    public String getMessage(Session session) {
        log.debug("Create delivery message type for session data with user : {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        String question = switch (session.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(DELIVERY_COURIER_QUESTION_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(DELIVERY_CLIENT_QUESTION_CODE);
        };
        String note = Objects.isNull(session.getDelivery()) ?
                EMPTY :
                lms.getLocaleMessage(DELIVERY_NOTE_CODE);
        return lms.getLocaleMessage(DELIVERY_DESCRIPTION_CODE) + question + note;
    }
}
