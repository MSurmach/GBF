package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.*;

@Service
public class DateStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        String[] split = callback.split(":");
        var command = BotButton.Calendar.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        switch (command) {
            case CHANGE_MONTH -> {
                if (currentState == DATE_FROM)
                    userData.setCurrentState(MONTH_FROM);
                else userData.setCurrentState(MONTH_TO);
            }
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                catchDate(userData, parsedDate);
                State nextState = selectNextState(userData);
                userData.setCurrentState(nextState);
            }
            case ALERT -> userData.setCurrentState(ALERT);
        }
    }

    private void catchDate(UserData userData, LocalDate date) {
        State currentState = userData.getCurrentState();
        if (currentState == DATE_FROM) userData.setDateFrom(date);
        else userData.setDateTo(date);
    }

    private State selectNextState(UserData userData) {
        Role role = userData.getRole();
        State currentState = userData.getCurrentState();
        return switch (role) {
            case CUSTOMER, COURIER -> currentState == DATE_FROM ? COUNTRY_TO : CARGO_MENU;
            case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
        };
    }
}
