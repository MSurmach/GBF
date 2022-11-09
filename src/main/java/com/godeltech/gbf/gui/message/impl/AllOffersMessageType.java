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

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class AllOffersMessageType implements MessageType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.ALL_OFFERS;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        Role role = sessionData.getRole();
        String username = sessionData.getUsername();
        Page<Offer> page = sessionData.getOffers();
        if (page == null || page.isEmpty()) return offersNotFoundMessage(role, username);
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(aboutMessage(role, username));
        for (Offer offer : page) {
            messageBuilder.append(offerIdMessage(offer.getRole(), offer.getId())).
                    append(offerHeaderMessage(offer.getRole(), offer.getTelegramUser().getUserName())).
                    append(routeDetails(offer.getRoutePoints(), lms)).
                    append(datesDetails(offer.getStartDate(), offer.getEndDate(), lms)).
                    append(deliveryDetails(offer.getDelivery(), lms)).
                    append(seatsDetails(offer.getSeats(), lms)).
                    append(commentDetails(offer.getComment(), lms)).
                    append(System.lineSeparator());
        }
        return messageBuilder.toString();
    }

    private String offersNotFoundMessage(Role role, String username) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(ALL_REGISTRATIONS_NOT_EXIST_CODE, username) :
                lms.getLocaleMessage(ALL_REQUESTS_NOT_EXIST_CODE, username);
    }

    private String offerHeaderMessage(Role role, String username) {
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(COURIER_HEADER, username) :
                lms.getLocaleMessage(CLIENT_HEADER, username);
    }

    private String offerIdMessage(Role role, Long offerId) {
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId.toString()) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId.toString());
    }
    private String aboutMessage(Role role, String username) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(ALL_REGISTRATIONS_EXIST_CODE, username) :
                lms.getLocaleMessage(ALL_REQUESTS_EXIST_CODE, username);
    }
}
