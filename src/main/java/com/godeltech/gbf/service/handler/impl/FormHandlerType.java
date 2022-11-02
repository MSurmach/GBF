package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.validator.RouteValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;

import static com.godeltech.gbf.model.State.FORM;

@Service
@AllArgsConstructor
public class FormHandlerType implements HandlerType {

    private final OfferService offerService;
    private RouteValidator routeValidator;

    @Override
    public State getState() {
        return FORM;
    }

    @Override
    public State handle(SessionData sessionData) {
        var callback = sessionData.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        switch (clicked) {
            case ADD_ROUTE, EDIT_ROUTE -> sessionData.setTempRoute(new LinkedList<>(sessionData.getRoute()));
            case ADD_DATES, EDIT_DATES -> {
                LocalDate startDate = sessionData.getStartDate();
                if (startDate != null) {
                    sessionData.setTempStartDate(LocalDate.from(startDate));
                    sessionData.setTempEndDate(LocalDate.from(sessionData.getEndDate()));
                }
            }
            case FORM_CONFIRM_AS_COURIER, FORM_CONFIRM_AS_REQUEST_VIEWER, FORM_CONFIRM_AS_REGISTRATION_VIEWER -> {
                routeValidator.checkRouteIsNotEmpty(sessionData.getRoute(), sessionData.getCallbackQueryId());
                offerService.save(sessionData);
            }
            case FORM_CONFIRM_AS_CLIENT -> {
                routeValidator.checkRouteIsNotEmpty(sessionData.getRoute(), sessionData.getCallbackQueryId());
                Offer offer = ModelUtils.mapSessionDataToOffer(sessionData);
                sessionData.setSearchOffer(offer);
            }
        }
        return clicked.getNextState();
    }
}
