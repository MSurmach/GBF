package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class OffersMessageType implements MessageType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.MY_OFFERS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Role role = sessionData.getRole();
        Page<Offer> page = sessionData.getOffers();
        if (page == null || page.isEmpty())
            return offersNotFoundMessage(role);
        Offer offer = page.getContent().get(0);
        return aboutMessage(sessionData) +
                offerHeaderWithIdMessage(offer.getRole(), offer.getId().toString()) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }

    private String offersNotFoundMessage(Role role) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE) :
                lms.getLocaleMessage(REQUESTS_NOT_EXIST_CODE);
    }

    private String offerHeaderWithIdMessage(Role role, String offerId) {
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId);
    }

    private String aboutMessage(SessionData sessionData) {
        return Objects.equals(sessionData.getRole(), Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, ModelUtils.getUserMention(sessionData)) :
                lms.getLocaleMessage(REQUESTS_EXIST_CODE, ModelUtils.getUserMention(sessionData));
    }
}
