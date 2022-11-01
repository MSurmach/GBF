package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryMessageType implements MessageType {

    public final static String DELIVERY_DESCRIPTION_CODE = "delivery.description";
    public final static String DELIVERY_COURIER_QUESTION_CODE = "delivery.courier.question";
    public final static String DELIVERY_CLIENT_QUESTION_CODE = "delivery.client.question";

    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DELIVERY;
    }

    @Override
    public String getMessage(UserData userData) {
        String question = switch (userData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(DELIVERY_COURIER_QUESTION_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(DELIVERY_CLIENT_QUESTION_CODE);
        };
        return lms.getLocaleMessage(DELIVERY_DESCRIPTION_CODE) + question;
    }
}
