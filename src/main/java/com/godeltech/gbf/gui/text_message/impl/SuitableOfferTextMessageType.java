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
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
public class SuitableOfferTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.NOTIFICATION;
    }

    @Override
    public String getMessage(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
       StringBuilder messageBuilder = new StringBuilder();
        return session.getProposedOffer()
                .map(offer -> messageBuilder
                        .append(aboutMessage(offer, lms))
                        .append(offerIdMessage(offer, lms))
                        .append(routeDetails(offer.getRoutePoints(), lms))
                        .append(datesDetails(offer.getStartDate(), offer.getEndDate(), lms))
                        .append(seatsDetails(offer.getSeats(), lms))
                        .append(commentDetails(offer.getComment(), lms))
                        .append(System.lineSeparator())
                        .toString())
                .orElseGet(() -> messageBuilder.append(offerNotValidMessage(lms)).toString());
    }

    private String offerNotValidMessage(LocalMessageSource lms) {
        return lms.getLocaleMessage(NOTIFICATION_OFFER_NOT_VALID);
    }
    private String offerIdMessage(Offer offer, LocalMessageSource lms) {
        return Objects.equals(offer.getRole(), Role.COURIER) ?
                lms.getLocaleMessage(NOTIFICATION_REGISTRATION_ID_CODE, offer.getId().toString()):
                lms.getLocaleMessage(NOTIFICATION_REQUEST_ID_CODE, offer.getId().toString());


    }

    private String aboutMessage(Offer offer, LocalMessageSource lms) {
        return Objects.equals(offer.getRole(), Role.COURIER) ?
                lms.getLocaleMessage(NOTIFICATION_REGISTRATION_ABOUT_CODE, ModelUtils.getUserMention(offer)):
                lms.getLocaleMessage(NOTIFICATION_REQUEST_ABOUT_CODE, ModelUtils.getUserMention(offer));


    }
}
