package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.State.CITY_TO;
import static com.godeltech.gbf.management.button.BotButton.Calendar.INIT;

@Service
public class CityStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        State currentState = userData.getCurrentState();
        if (currentState == CITY_TO) userData.setCityTo(callback);
        else userData.setCityFrom(callback);
        StateFlow stateFlow = userData.getStateFlow();
        userData.setPreviousState(currentState);
        userData.setCurrentState(stateFlow.getNextState(currentState));
        userData.setCallback(INIT + ":" + LocalDate.now());
    }
}
