package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class OffersMessageType implements MessageType {
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_ID_CODE = "registration.id";

    public final static String REQUESTS_EXIST_CODE = "requests.exist";
    public final static String REQUESTS_NOT_EXIST_CODE = "requests.notExist";
    public final static String REQUEST_ID_CODE = "request.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.OFFERS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Role role = sessionData.getRole();
        String username = sessionData.getUsername();
        Page<Offer> page = sessionData.getPage();
        if (page == null || page.isEmpty()) return offersNotFoundMessage(role, username);
        Offer offer = page.getContent().get(0);
        return aboutMessage(role, username) +
                offerHeaderWithIdMessage(role, offer.getId().toString()) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }

    private String offersNotFoundMessage(Role role, String username) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username) :
                lms.getLocaleMessage(REQUESTS_NOT_EXIST_CODE, username);
    }

    private String offerHeaderWithIdMessage(Role role, String offerId) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId);
    }

    private String aboutMessage(Role role, String username) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) :
                lms.getLocaleMessage(REQUESTS_EXIST_CODE, username);
    }
}
