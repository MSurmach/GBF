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
public class OffersTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.MY_OFFERS;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        Role role = session.getRole();
        Page<Offer> page = session.getOffers();
        if (page == null || page.isEmpty())
            return offersNotFoundMessage(role, lms);
        Offer offer = page.getContent().get(0);
        return aboutMessage(session, lms) +
                offerHeaderWithIdMessage(offer.getRole(), offer.getId().toString(), lms) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }

    private String offersNotFoundMessage(Role role, LocalMessageSource lms) {
        return Objects.equals(role, Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE) :
                lms.getLocaleMessage(REQUESTS_NOT_EXIST_CODE);
    }

    private String offerHeaderWithIdMessage(Role role, String offerId, LocalMessageSource lms) {
        return Objects.equals(role, Role.COURIER) ?
                lms.getLocaleMessage(REGISTRATION_ID_CODE, offerId) :
                lms.getLocaleMessage(REQUEST_ID_CODE, offerId);
    }

    private String aboutMessage(Session session, LocalMessageSource lms) {
        return Objects.equals(session.getRole(), Role.REGISTRATIONS_VIEWER) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, ModelUtils.getUserMention(session)) :
                lms.getLocaleMessage(REQUESTS_EXIST_CODE, ModelUtils.getUserMention(session));
    }
}
