package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.message.impl.RegistrationsMessageType.REGISTRATION_DATA_ID;
import static com.godeltech.gbf.gui.message.impl.RequestsMessageType.REQUESTS_DATA_ID;

@Component
@AllArgsConstructor
public class FormMessageType implements MessageType {
    public final static String COURIER_INSTRUCTION_CODE = "form.courier.instruction";
    public final static String COURIER_ESSENTIAL_INFO_CODE = "form.courier.essential.info";
    public final static String CLIENT_INSTRUCTION_CODE = "form.client.instruction";
    public final static String CLIENT_ESSENTIAL_INFO_CODE = "form.client.essential.info";
    public final static String DETAILS_HEADER_EMPTY_CODE = "form.details.header.empty";
    public final static String DETAILS_HEADER_FULL_CODE = "form.details.header.full";
    public final static String REGISTRATIONS_VIEWER_INSTRUCTION_CODE = "form.registrationsViewer.instruction";
    public final static String REQUESTS_VIEWER_INSTRUCTION_CODE = "form.requestsViewer.instruction";
    private LocalMessageSource lms;
    private DetailsCreator detailsCreator;

    @Override
    public String initialMessage(UserData userData) {
        Role currentRole = userData.getRole();
        return switch (currentRole) {
            case COURIER -> lms.getLocaleMessage(COURIER_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(COURIER_ESSENTIAL_INFO_CODE);
            case CLIENT -> lms.getLocaleMessage(CLIENT_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(CLIENT_ESSENTIAL_INFO_CODE);
            case REGISTRATIONS_VIEWER ->
                    lms.getLocaleMessage(REGISTRATIONS_VIEWER_INSTRUCTION_CODE, userData.getUsername());
            case REQUESTS_VIEWER -> lms.getLocaleMessage(REQUESTS_VIEWER_INSTRUCTION_CODE, userData.getUsername());
        };
    }

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(UserData userData) {
        return header(userData) +
                detailsCreator.createAllDetails(userData);
    }

    private String header(UserData userData) {
//        if (userData.isEmpty()) return lms.getLocaleMessage(DETAILS_HEADER_EMPTY_CODE);
        return switch (userData.getRole()) {
            case COURIER, CLIENT -> lms.getLocaleMessage(DETAILS_HEADER_FULL_CODE);
            case REGISTRATIONS_VIEWER -> lms.getLocaleMessage(REGISTRATION_DATA_ID, userData.getId().toString());
            case REQUESTS_VIEWER -> lms.getLocaleMessage(REQUESTS_DATA_ID, userData.getId().toString());
        };
    }
}
