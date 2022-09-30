package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.button.BotButton.Calendar.INIT;
import static com.godeltech.gbf.model.State.*;

@Service
public class CityStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        catchCity(userData, callback);
        State nextState = switchState(userData);
        userData.setCurrentState(nextState);
        userData.setCallback(INIT + ":" + LocalDate.now());
    }

    private void catchCity(UserData userData, String city) {
        State currentState = userData.getCurrentState();
        if (currentState == CITY_FROM) userData.setCityFrom(city);
        else userData.setCityTo(city);
    }

    private State switchState(UserData userData) {
        Role role = userData.getRole();
        State currentState = userData.getCurrentState();
        return switch (role) {
            case COURIER -> currentState == CITY_FROM ? DATE_FROM : DATE_TO;
            case CUSTOMER -> currentState == CITY_FROM ? DATE_FROM_QUIZ : DATE_TO_QUIZ;
            case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
        };
    }
}
