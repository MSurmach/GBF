package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
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
    public State handle(Session session) {
        var callback = session.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        switch (clicked) {
            case ADD_ROUTE, EDIT_ROUTE -> session.setTempRoute(new LinkedList<>(session.getRoute()));
            case ADD_DATES, EDIT_DATES -> {
                LocalDate startDate = session.getStartDate();
                if (startDate != null) {
                    session.setTempStartDate(LocalDate.from(startDate));
                    session.setTempEndDate(LocalDate.from(session.getEndDate()));
                }
            }
            case REGISTER -> {
                routeValidator.checkRouteIsNotEmpty(session.getRoute(), session.getCallbackQueryId(), session.getTelegramUser().getLanguage());
                offerService.save(session);
            }
            case SAVE_CHANGES -> {
                routeValidator.checkRouteIsNotEmpty(session.getRoute(), session.getCallbackQueryId(), session.getTelegramUser().getLanguage());
                session.setEditable(false);
                offerService.save(session);
                switch (session.getRole()) {
                    case COURIER -> session.setRole(Role.REGISTRATIONS_VIEWER);
                    case CLIENT -> session.setRole(Role.REQUESTS_VIEWER);
                }
            }
            case SEARCH_CLIENTS -> {
                routeValidator.checkRouteIsNotEmpty(session.getRoute(), session.getCallbackQueryId(), session.getTelegramUser().getLanguage());
                Offer offer = ModelUtils.mapSessionDataToOffer(session);
                session.setSearchOffer(offer);
                offerService.save(session);
            }
        }
        return clicked.getNextState();
    }

}
