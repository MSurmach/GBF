package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.OFFER_ID_REGISTRATION_NOT_FOUND_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.OFFER_ID_REQUEST_NOT_FOUND_CODE;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;

@Component
@AllArgsConstructor
public class OfferByIdNotFoundTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.OFFER_BY_ID_NOT_FOUND;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        Long tempOfferIdSearch = session.getTempOfferId();
        return Objects.equals(session.getRole(), REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(OFFER_ID_REGISTRATION_NOT_FOUND_CODE, tempOfferIdSearch.toString()) :
                lms.getLocaleMessage(OFFER_ID_REQUEST_NOT_FOUND_CODE, tempOfferIdSearch.toString());
    }
}