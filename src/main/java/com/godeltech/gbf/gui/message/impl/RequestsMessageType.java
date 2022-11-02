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
public class RequestsMessageType implements MessageType, PaginationInfo<Offer> {
    public final static String REQUESTS_EXIST_INITIAL = "requests.exist.initial";
    public final static String REQUESTS_NOT_EXIST_INITIAL = "requests.notExist.initial";
    public final static String REQUESTS_DATA_ID = "request.data.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REQUESTS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(REQUESTS_DATA_ID, sessionData.getOfferId().toString()) +
                routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    public String initialMessage(SessionData sessionData) {
        Page<Offer> pages = sessionData.getPage();
        String username = sessionData.getUsername();
        return (pages != null && !pages.isEmpty()) ?
                lms.getLocaleMessage(REQUESTS_EXIST_INITIAL, username) +
                        paginationInfoLocalMessage(pages, lms) :
                lms.getLocaleMessage(REQUESTS_NOT_EXIST_INITIAL, username);
    }
}
