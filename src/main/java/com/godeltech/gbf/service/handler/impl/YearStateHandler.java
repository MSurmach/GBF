package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class YearStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        if (currentState == State.YEAR_TO) userData.setCurrentState(State.MONTH_TO);
        else userData.setCurrentState(State.MONTH_FROM);
    }
}
