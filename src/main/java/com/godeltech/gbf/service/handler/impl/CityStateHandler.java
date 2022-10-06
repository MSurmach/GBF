package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CityStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        catchCity(userData);
        return switchState(userData);
    }

    private void catchCity(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        if (currentState == CITY_FROM) userData.setCityFrom(callback);
        else userData.setCityTo(callback);
    }

    private State switchState(UserData userData) {
        Role role = userData.getRole();
        State currentState = userData.getStateHistory().peek();
        return switch (role) {
            case COURIER -> currentState == CITY_FROM ? DATE_FROM : DATE_TO;
            case CLIENT -> currentState == CITY_FROM ? DATE_FROM_QUIZ : DATE_TO_QUIZ;
            case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
            case REQUESTS_VIEWER -> REQUEST_EDITOR;
        };
    }
}
