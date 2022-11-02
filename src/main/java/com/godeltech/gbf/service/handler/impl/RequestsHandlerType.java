package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RequestButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RequestsHandlerType implements HandlerType {

    private OfferService offerService;

    @Override
    public State getState() {
        return REQUESTS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        String[] splitCallback = callback.split(":");
        RequestButton clickedButton = RequestButton.valueOf(splitCallback[0]);
        long offerId = Long.parseLong(splitCallback[1]);
        sessionData.setPageNumber(0);
        return switch (clickedButton) {
            case REQUEST_EDIT -> {
                Offer toEditOffer = sessionData.getPage().getContent().get(0);
                ModelUtils.copyOfferToSessionData(sessionData, toEditOffer);
                sessionData.setRole(Role.REQUESTS_VIEWER);
                yield FORM;
            }
            case REQUEST_DELETE -> {
                offerService.deleteOfferById(offerId);
                yield REQUESTS;
            }
            case REQUEST_FIND_COURIERS -> {
                Offer toFindOffer = sessionData.getPage().getContent().get(0);
                sessionData.setTempForSearch(toFindOffer);
                yield COURIERS_LIST_RESULT;
            }
        };
    }
}
