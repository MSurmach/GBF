package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
@Slf4j
public class FormMessageType implements MessageType {


    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create form message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return instructions(sessionData.getRole(), sessionData.getUsername()) +
                detailsHeader(sessionData.getRole(), sessionData.isEmpty(), sessionData.getOfferId()) +
                routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    private String detailsHeader(Role role, boolean isEmptyData, Long userId) {

        if (isEmptyData) return lms.getLocaleMessage(DETAILS_HEADER_EMPTY_CODE);
        return switch (role) {
            case COURIER, CLIENT -> lms.getLocaleMessage(DETAILS_HEADER_FULL_CODE);
            case REGISTRATIONS_VIEWER -> lms.getLocaleMessage(REGISTRATION_DATA_ID, userId.toString());
            case REQUESTS_VIEWER -> lms.getLocaleMessage(REQUESTS_DATA_ID, userId.toString());
        };
    }

    private String instructions(Role role, String username) {
        return switch (role) {
            case COURIER -> lms.getLocaleMessage(INSTRUCTION_COURIER_ABOUT_CODE, username);
            case CLIENT -> lms.getLocaleMessage(INSTRUCTION_CLIENT_ABOUT_CODE, username);
            case REGISTRATIONS_VIEWER -> lms.getLocaleMessage(REGISTRATIONS_VIEWER_INSTRUCTION_CODE, username);
            case REQUESTS_VIEWER -> lms.getLocaleMessage(REQUESTS_VIEWER_INSTRUCTION_CODE, username);
        };
    }
}
