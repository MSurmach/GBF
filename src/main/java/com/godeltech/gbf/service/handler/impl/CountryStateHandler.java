package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class CountryStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        State currentState = userData.getCurrentState();
        if (currentState == State.COUNTRY_FROM) {
            userData.setCountryFrom(callback);
        } else {
            userData.setCountryTo(callback);
            userData.setPreviousState(currentState);
        }
        StateFlow stateFlow = userData.getStateFlow();
        State nextState = stateFlow.getNextState(currentState);
        userData.setCurrentState(nextState);

    }
}
