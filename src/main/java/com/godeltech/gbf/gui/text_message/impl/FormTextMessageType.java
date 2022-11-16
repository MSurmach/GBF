package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.model.ModelUtils;
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
public class FormTextMessageType implements TextMessageType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return instructions(sessionData) +
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

    private String instructions(SessionData sessionData) {
        String message = null;
        switch (sessionData.getRole()) {
            case COURIER -> message = !sessionData.isEditable() ?
                    lms.getLocaleMessage(INSTRUCTION_COURIER_ABOUT_CODE, ModelUtils.getUserMention(sessionData)) :
                    lms.getLocaleMessage(INSTRUCTION_REGISTRATIONS_VIEWER_ABOUT_CODE, ModelUtils.getUserMention(sessionData));
            case CLIENT -> message = !sessionData.isEditable() ?
                    lms.getLocaleMessage(INSTRUCTION_CLIENT_ABOUT_CODE, ModelUtils.getUserMention(sessionData)) :
                    lms.getLocaleMessage(INSTRUCTION_REQUESTS_VIEWER_ABOUT_CODE, ModelUtils.getUserMention(sessionData));
        }
        return message;
    }
}
