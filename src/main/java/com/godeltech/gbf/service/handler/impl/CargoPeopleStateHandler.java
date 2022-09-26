package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.management.State.CARGO_MENU;

@Service
public class CargoPeopleStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        userData.setCompanionCount(Integer.parseInt(callback));
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(CARGO_MENU);
    }
}
