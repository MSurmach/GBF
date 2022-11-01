package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;
import static com.godeltech.gbf.gui.message.impl.RegistrationsMessageType.REGISTRATION_DATA_ID;
import static com.godeltech.gbf.gui.message.impl.RequestsMessageType.REQUESTS_DATA_ID;

@Component
@AllArgsConstructor
public class FormMessageType implements MessageType {
    public final static String INSTRUCTION_COURIER_ABOUT_CODE = "form.instruction.courier.about";
    public final static String INSTRUCTION_CLIENT_ABOUT_CODE = "form.instruction.client.about";
    public final static String DETAILS_HEADER_EMPTY_CODE = "form.details.header.empty";
    public final static String DETAILS_HEADER_FULL_CODE = "form.details.header.full";
    public final static String REGISTRATIONS_VIEWER_INSTRUCTION_CODE = "form.registrationsViewer.instruction";
    public final static String REQUESTS_VIEWER_INSTRUCTION_CODE = "form.requestsViewer.instruction";

    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return instructions(sessionData.getRole(), sessionData.getUsername()) +
                detailsHeader(sessionData.getRole(), sessionData.isEmpty(), sessionData.getId()) +
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
