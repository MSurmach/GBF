package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class RegistrationsMessageType implements MessageType, PaginationInfo<Offer> {
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_DATA_ID = "registration.data.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REGISTRATIONS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(REGISTRATION_DATA_ID, sessionData.getOfferId().toString()) +
                routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    public String initialMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        String username = sessionData.getUsername();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username);
    }
}
