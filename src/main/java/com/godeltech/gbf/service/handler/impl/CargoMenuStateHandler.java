package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class CargoMenuStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        var command = BotButton.Cargo.valueOf(callback);
        State currentState = userData.getCurrentState();
        switch (command) {
            case CONFIRM_CARGO -> {
                StateFlow stateFlow = userData.getStateFlow();
                userData.setCurrentState(stateFlow.getNextState(currentState));
            }
            case SELECT_DOCUMENTS -> {
                userData.setDocuments(true);
            }
            case CANCEL_DOCUMENTS -> {
                userData.setDocuments(false);
            }
            case SELECT_PACKAGE, EDIT_PACKAGE -> {
                userData.setPreviousState(currentState);
                userData.setCurrentState(State.CARGO_PACKAGE);
            }
            case CANCEL_PACKAGE -> {
                userData.setPackageSize(null);
            }
            case SELECT_PEOPLE, EDIT_PEOPLE -> {
                userData.setPreviousState(currentState);
                userData.setCurrentState(State.CARGO_PEOPLE);
            }
            case CANCEL_PEOPLE -> userData.setCompanionCount(0);
        }
    }
}
