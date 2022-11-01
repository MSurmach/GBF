package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
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
    public State handle(UserData userData) {
        var callback = userData.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        switch (clicked) {
            case ADD_ROUTE, EDIT_ROUTE -> userData.setTempRoute(new LinkedList<>(userData.getRoute()));
            case ADD_DATES, EDIT_DATES -> {
                LocalDate startDate = userData.getStartDate();
                if (startDate != null) {
                    userData.setTempStartDate(LocalDate.from(startDate));
                    userData.setTempEndDate(LocalDate.from(userData.getEndDate()));
                }
            }
            case FORM_CONFIRM_AS_COURIER, FORM_CONFIRM_AS_CLIENT, FORM_CONFIRM_AS_REGISTRATION_VIEWER -> {
                routeValidator.checkRouteIsNotEmpty(userData.getRoute(), userData.getCallbackQueryId());
                offerService.save(userData);
            }
        }
        return clicked.getNextState();
    }
}
