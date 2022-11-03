package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SeatsMessageType implements MessageType {

    private final static String SEATS_COURIER_CODE = "seats.courier";
    private final static String SEATS_CLIENT_CODE = "seats.client";
    private final static String SEATS_INFO_CODE = "seats.info";
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.SEATS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        String info = lms.getLocaleMessage(SEATS_INFO_CODE);
        String roleQuestion = switch (sessionData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(SEATS_COURIER_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(SEATS_CLIENT_CODE);
        };
        return info + roleQuestion;
    }
}
