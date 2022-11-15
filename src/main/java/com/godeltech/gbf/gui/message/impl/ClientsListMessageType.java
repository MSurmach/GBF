package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
public class ClientsListMessageType implements MessageType {


    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.CLIENTS_SEARCH_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getOffers();
        String username = sessionData.getUsername();
        Long userId = sessionData.getTelegramUserId();
        if (page == null || page.isEmpty())
            return lms.getLocaleMessage(CLIENTS_NOT_EXIST_CODE);
        Offer offer = page.getContent().get(0);
        return lms.getLocaleMessage(CLIENTS_EXIST_CODE, ModelUtils.getUserMention(sessionData)) +
                lms.getLocaleMessage(CLIENT_HEADER, ModelUtils.getUserMention(offer)) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }
}
