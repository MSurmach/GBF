package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CargoMenuStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        var command = BotButton.Cargo.valueOf(callback);
        switch (command) {
            case CONFIRM_CARGO -> {
                Role role = userData.getRole();
                State nextState = switch (role) {
                    case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
                    case CUSTOMER -> FIND_COURIER;
                    case COURIER -> COMMENT_QUIZ;
                };
                userData.setCurrentState(nextState);
            }
            case SELECT_DOCUMENTS -> userData.setDocuments(true);
            case CANCEL_DOCUMENTS -> userData.setDocuments(false);
            case SELECT_PACKAGE, EDIT_PACKAGE -> userData.setCurrentState(State.CARGO_PACKAGE);
            case CANCEL_PACKAGE -> userData.setPackageSize(null);
            case SELECT_PEOPLE, EDIT_PEOPLE -> userData.setCurrentState(State.CARGO_PEOPLE);
            case CANCEL_PEOPLE -> userData.setCompanionCount(0);
        }
    }
}
