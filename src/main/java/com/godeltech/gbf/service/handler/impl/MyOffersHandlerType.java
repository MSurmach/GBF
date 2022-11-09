package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.OffersBotButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MY_OFFERS;

@Service
@AllArgsConstructor
public class MyOffersHandlerType implements HandlerType {
    private final OfferService offerService;

    @Override
    public State getState() {
        return MY_OFFERS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = OffersBotButton.valueOf(splittedCallback[0]);
        long offerId = Long.parseLong(splittedCallback[1]);
        sessionData.setPageNumber(0);
        switch (clickedButton) {
            case OFFER_EDIT -> {
                Offer toEditOffer = sessionData.getOffers().getContent().get(0);
                ModelUtils.copyOfferToSessionData(sessionData, toEditOffer);
                sessionData.setEditable(true);
            }
            case OFFER_DELETE -> offerService.deleteOfferById(offerId);
            case OFFER_FIND_CLIENTS, OFFER_FIND_COURIERS -> {
                Offer toFindOffer = sessionData.getOffers().getContent().get(0);
                sessionData.setSearchOffer(toFindOffer);
            }
        }
        return clickedButton.getNextState();
    }
}
