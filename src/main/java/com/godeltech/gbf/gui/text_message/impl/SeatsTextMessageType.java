package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

@Component
@AllArgsConstructor
@Slf4j
public class SeatsTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.SEATS;
    }

    @Override
    public String getMessage(Session session) {
        log.debug("Create seats message type for session data with user: {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        String info = lms.getLocaleMessage(SEATS_INFO_CODE);
        String roleQuestion = switch (session.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(SEATS_COURIER_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(SEATS_CLIENT_CODE);
        };
        return info + roleQuestion;
    }
}
