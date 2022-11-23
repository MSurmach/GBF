package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.OfferNotFoundException;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MY_OFFERS;
import static com.godeltech.gbf.model.State.OFFER_BY_ID_NOT_FOUND;

@Service
@AllArgsConstructor
public class OfferIdInputHandlerType implements HandlerType {
    private final OfferService offerService;

    @Override
    public State getState() {
        return State.OFFER_ID_INPUT;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        try {
            Long offerId = Long.parseLong(callback);
            session.setTempOfferId(offerId);
            Role role = session.getRole().equals(Role.REGISTRATIONS_VIEWER) ? Role.COURIER : Role.CLIENT;
            int pageNumber = offerService.getOrderedNumberOfOfferWithId(session.getTelegramUser().getId(), role, offerId);
            session.setPageNumber(pageNumber);
            return MY_OFFERS;
        } catch (NumberFormatException numberFormatException) {
            return session.getStateHistory().pop();
        }
        catch (OfferNotFoundException exception){
            return OFFER_BY_ID_NOT_FOUND;
        }
    }
}
