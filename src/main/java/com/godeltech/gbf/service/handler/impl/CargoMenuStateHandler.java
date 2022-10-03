package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.CargoBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CargoMenuStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        var clickedButton = CargoBotButton.valueOf(callback);
        return switch (clickedButton) {
            case CONFIRM_CARGO -> {
                Role role = userData.getRole();
                yield switch (role) {
                    case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
                    case CUSTOMER -> FIND_COURIER;
                    case COURIER -> COMMENT_QUIZ;
                };
            }
            case SELECT_DOCUMENTS -> {
                userData.setDocuments(true);
                yield currentState;
            }
            case CANCEL_DOCUMENTS -> {
                userData.setDocuments(false);
                yield currentState;
            }
            case SELECT_PACKAGE, EDIT_PACKAGE -> CARGO_PACKAGE;
            case CANCEL_PACKAGE -> {
                userData.setPackageSize(null);
                yield currentState;
            }
            case SELECT_PEOPLE, EDIT_PEOPLE -> CARGO_PEOPLE;
            case CANCEL_PEOPLE -> {
                userData.setCompanionCount(0);
                yield currentState;
            }
        };
    }
}
