package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class CouriersListMessageType implements MessageType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.COURIERS_SEARCH_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        String username = sessionData.getUsername();
        if (page == null || page.isEmpty()) return lms.getLocaleMessage(COURIERS_NOT_EXIST, username);
        Offer offer = page.getContent().get(0);
        return lms.getLocaleMessage(COURIERS_EXIST, username) +
                lms.getLocaleMessage(COURIER_HEADER, offer.getTelegramUser().getUserName()) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }
}
