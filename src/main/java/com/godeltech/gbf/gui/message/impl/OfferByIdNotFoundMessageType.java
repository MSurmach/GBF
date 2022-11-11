package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.OFFER_ID_REGISTRATION_NOT_FOUND_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.OFFER_ID_REQUEST_NOT_FOUND_CODE;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;

@Component
@AllArgsConstructor
public class OfferByIdNotFoundMessageType implements MessageType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.OFFER_BY_ID_NOT_FOUND;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Long tempOfferIdSearch = sessionData.getTempOfferId();
        return Objects.equals(sessionData.getRole(), REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(OFFER_ID_REGISTRATION_NOT_FOUND_CODE, tempOfferIdSearch.toString()):
                lms.getLocaleMessage(OFFER_ID_REQUEST_NOT_FOUND_CODE, tempOfferIdSearch.toString());
    }
}