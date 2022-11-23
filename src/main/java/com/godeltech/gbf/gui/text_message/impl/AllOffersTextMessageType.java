package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
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
public class AllOffersTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.ALL_OFFERS;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        Role role = session.getRole();
        String username = session.getTelegramUser().getUserName();
        Page<Offer> page = session.getOffers();
        if (page == null || page.isEmpty()) return offersNotFoundMessage(role, username, lms);
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(aboutMessage(session, lms));
        for (Offer offer : page) {
            messageBuilder.append(offerIdMessage(offer, lms)).
                    append(offerHeaderMessage(offer, lms)).
                    append(routeDetails(offer.getRoutePoints(), lms)).
                    append(datesDetails(offer.getStartDate(), offer.getEndDate(), lms)).
                    append(deliveryDetails(offer.getDelivery(), lms)).
                    append(seatsDetails(offer.getSeats(), lms)).
                    append(commentDetails(offer.getComment(), lms)).
                    append(System.lineSeparator());
        }
        return messageBuilder.toString();
    }

    private String offerIdMessage(Offer offer, LocalMessageSource lms) {
        return Objects.equals(offer.getRole(), Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offer.getId().toString()) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offer.getId().toString());
    }

    private String offersNotFoundMessage(Role role, String username, LocalMessageSource lms) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(ALL_REGISTRATIONS_NOT_EXIST_CODE, username) :
                lms.getLocaleMessage(ALL_REQUESTS_NOT_EXIST_CODE, username);
    }

    private String offerHeaderMessage(Offer offer, LocalMessageSource lms) {
        return Objects.equals(offer.getRole(), Role.COURIER) ?
                lms.getLocaleMessage(COURIER_HEADER_CODE, ModelUtils.getUserMention(offer)) :
                lms.getLocaleMessage(CLIENT_HEADER_CODE, ModelUtils.getUserMention(offer));
    }

    private String aboutMessage(Session session, LocalMessageSource lms) {
        return Objects.equals(session.getRole(), Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(ALL_REGISTRATIONS_EXIST_CODE, ModelUtils.getUserMention(session)) :
                lms.getLocaleMessage(ALL_REQUESTS_EXIST_CODE, ModelUtils.getUserMention(session));
    }
}
