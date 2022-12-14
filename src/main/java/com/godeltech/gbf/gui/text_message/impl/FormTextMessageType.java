package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
public class FormTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        return instructions(session, lms) +
                detailsHeader(session.getRole(), session.isEmpty(), session.getOfferId(), session.isEditable(), lms) +
                routeDetails(session.getRoute(), lms) +
                datesDetails(session.getStartDate(), session.getEndDate(), lms) +
                deliveryDetails(session.getDelivery(), lms) +
                seatsDetails(session.getSeats(), lms) +
                commentDetails(session.getComment(), lms);
    }

    private String detailsHeader(Role role, boolean isEmptySession, Long offerId, boolean isEditable, LocalMessageSource lms) {
        if (isEmptySession) return lms.getLocaleMessage(DETAILS_HEADER_EMPTY_CODE);
        if (!isEditable) return lms.getLocaleMessage(DETAILS_HEADER_FULL_CODE);
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId.toString()) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId.toString());
    }

    private String instructions(Session session, LocalMessageSource lms) {
        String message = null;
        switch (session.getRole()) {
            case COURIER -> message = !session.isEditable() ?
                    lms.getLocaleMessage(INSTRUCTION_COURIER_ABOUT_CODE, ModelUtils.getUserMention(session)) :
                    lms.getLocaleMessage(INSTRUCTION_REGISTRATIONS_VIEWER_ABOUT_CODE, ModelUtils.getUserMention(session));
            case CLIENT -> message = !session.isEditable() ?
                    lms.getLocaleMessage(INSTRUCTION_CLIENT_ABOUT_CODE, ModelUtils.getUserMention(session)) :
                    lms.getLocaleMessage(INSTRUCTION_REQUESTS_VIEWER_ABOUT_CODE, ModelUtils.getUserMention(session));
        }
        return message;
    }
}
