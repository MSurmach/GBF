package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
public class FormMessageType implements MessageType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return instructions(sessionData.getRole(), sessionData.getUsername(), sessionData.isEditable()) +
                detailsHeader(sessionData.getRole(), sessionData.isEmpty(), sessionData.getOfferId(), sessionData.isEditable()) +
                routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    private String detailsHeader(Role role, boolean isEmptySession, Long offerId, boolean isEditable) {
        if (isEmptySession) return lms.getLocaleMessage(DETAILS_HEADER_EMPTY_CODE);
        if (!isEditable) return lms.getLocaleMessage(DETAILS_HEADER_FULL_CODE);
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId.toString()) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId.toString());
    }

    private String instructions(Role role, String username, boolean isEditable) {
        String message = null;
        switch (role) {
            case COURIER -> message = !isEditable ?
                    lms.getLocaleMessage(INSTRUCTION_COURIER_ABOUT_CODE, username) :
                    lms.getLocaleMessage(INSTRUCTION_REGISTRATIONS_VIEWER_ABOUT_CODE, username);
            case CLIENT -> message = !isEditable ?
                    lms.getLocaleMessage(INSTRUCTION_CLIENT_ABOUT_CODE, username) :
                    lms.getLocaleMessage(INSTRUCTION_REQUESTS_VIEWER_ABOUT_CODE, username);
        }
        return message;
    }
}
