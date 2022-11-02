package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RegistrationBotButton;
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
public class RegistrationsHandlerType implements HandlerType {
    private final OfferService offerService;

    @Override
    public State getState() {
        return REGISTRATIONS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        long offerId = Long.parseLong(splittedCallback[1]);
        sessionData.setPageNumber(0);
        return switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                Offer toEditOffer = sessionData.getPage().getContent().get(0);
                ModelUtils.copyOfferToSessionData(sessionData, toEditOffer);
                sessionData.setRole(Role.REGISTRATIONS_VIEWER);
                yield FORM;
            }
            case REGISTRATION_DELETE -> {
                offerService.deleteOfferById(offerId);
                yield REGISTRATIONS;
            }
            case REGISTRATION_FIND_CLIENTS -> {
                Offer toFindOffer = sessionData.getPage().getContent().get(0);
                sessionData.setTempForSearch(toFindOffer);
                yield CLIENTS_LIST_RESULT;
            }
        };
    }
}
