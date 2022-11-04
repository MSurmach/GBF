package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
public class ClientsListMessageType implements MessageType {
    public final static String CLIENTS_EXIST_CODE = "clients.exist";
    public final static String CLIENTS_NOT_EXIST_CODE = "clients.notExist";
    public final static String CLIENT_HEADER = "client.header";

    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.CLIENTS_LIST_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        String username = sessionData.getUsername();
        if (page == null || page.isEmpty()) return lms.getLocaleMessage(CLIENTS_NOT_EXIST_CODE, username);
        Offer offer = page.getContent().get(0);
        return lms.getLocaleMessage(CLIENTS_EXIST_CODE, username) +
                lms.getLocaleMessage(CLIENT_HEADER, offer.getTelegramUser().getUserName()) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }
}
