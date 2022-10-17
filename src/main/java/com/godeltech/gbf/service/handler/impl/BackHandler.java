package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class BackHandler implements Handler {
    @Override
    public State handle(UserData userData) {
        State currentState = userData.getStateHistory().pop();
        return switch (currentState) {
            case BACK -> null;
            case FORM -> MENU;
            case ROUTE_POINT_FORM -> FORM;
            case INTERMEDIATE_EDITOR -> FORM;
            case MENU -> null;
            case SUCCESS -> null;
            case WRONG_INPUT -> MENU;
            case COMMENT -> FORM;
            case COUNTRY -> ROUTE_POINT_FORM;
            case CITY -> ROUTE_POINT_FORM;
            case YEAR -> DATE;
            case MONTH -> DATE;
            case DATE -> ROUTE_POINT_FORM;
            case CARGO_MENU -> FORM;
            case CARGO_PACKAGE -> CARGO_MENU;
            case CARGO_PEOPLE -> CARGO_MENU;
            case REGISTRATIONS -> MENU;
            case REQUESTS -> MENU;
            case COURIERS_LIST_RESULT -> REQUESTS;
            case CLIENTS_LIST_RESULT -> REGISTRATIONS;
        };
    }
}
