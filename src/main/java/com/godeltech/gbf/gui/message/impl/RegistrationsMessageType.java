package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class RegistrationsMessageType implements MessageType {
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_DATA_ID_CODE = "registration.data.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REGISTRATIONS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        String username = sessionData.getUsername();
        if (page == null || page.isEmpty()) return lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username);
        Offer offer = page.getContent().get(0);
        return lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) +
                lms.getLocaleMessage(REGISTRATION_DATA_ID_CODE, offer.getId().toString()) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }
}
