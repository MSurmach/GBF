package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.OffersBotButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
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
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = OffersBotButton.valueOf(splittedCallback[0]);
        session.setPageNumber(0);
        switch (clickedButton) {
            case OFFER_EDIT -> {
                Offer toEditOffer = session.getOffers().getContent().get(0);
                ModelUtils.copyOfferToSessionData(session, toEditOffer);
                session.setEditable(true);
            }
            case OFFER_DELETE -> {
                long offerId = Long.parseLong(splittedCallback[1]);
                offerService.deleteOfferById(offerId);
            }
            case OFFER_FIND_CLIENTS, OFFER_FIND_COURIERS -> {
                Offer toFindOffer = session.getOffers().getContent().get(0);
                session.setSearchOffer(toFindOffer);
            }
        }
        return clickedButton.getNextState();
    }
}
