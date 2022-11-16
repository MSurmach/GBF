package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;
import static com.godeltech.gbf.model.Role.*;

@Component
@AllArgsConstructor
public class SearchResultTextMessageType implements TextMessageType {
    private LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.SEARCH_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        Page<Offer> page = sessionData.getOffers();
        if (page == null || page.isEmpty())
            return messageIfSearchResultIsEmpty(sessionData.getRole(), lms);
        Offer offer = page.getContent().get(0);
        return messageIfSearchResultIsNotEmpty(sessionData, lms) +
                offerDetailsHeader(offer, lms) +
                routeDetails(offer.getRoutePoints(), lms) +
                datesDetails(offer.getStartDate(), offer.getEndDate(), lms) +
                deliveryDetails(offer.getDelivery(), lms) +
                seatsDetails(offer.getSeats(), lms) +
                commentDetails(offer.getComment(), lms);
    }

    private String messageIfSearchResultIsEmpty(Role role, LocalMessageSource lms) {
        return Objects.equals(role, REGISTRATIONS_VIEWER) || Objects.equals(role, COURIER) ?
                lms.getLocaleMessage(CLIENTS_NOT_EXIST_CODE) :
                lms.getLocaleMessage(COURIERS_NOT_EXIST_CODE);
    }

    private String messageIfSearchResultIsNotEmpty(SessionData sessionData, LocalMessageSource lms) {
        return Objects.equals(sessionData.getRole(), REGISTRATIONS_VIEWER) || Objects.equals(sessionData.getRole(), COURIER) ?
                lms.getLocaleMessage(CLIENTS_EXIST_CODE, ModelUtils.getUserMention(sessionData)) :
                lms.getLocaleMessage(COURIERS_EXIST_CODE, ModelUtils.getUserMention(sessionData));
    }

    private String offerDetailsHeader(Offer offer, LocalMessageSource lms) {
        return Objects.equals(offer.getRole(), CLIENT) ?
                lms.getLocaleMessage(CLIENT_HEADER_CODE, ModelUtils.getUserMention(offer)) :
                lms.getLocaleMessage(COURIER_HEADER_CODE, ModelUtils.getUserMention(offer));
    }

}
