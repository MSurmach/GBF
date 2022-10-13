package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.ConfirmationException;
import com.godeltech.gbf.gui.button.CargoBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CargoMenuHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        var clickedButton = CargoBotButton.valueOf(callback);
        return switch (clickedButton) {
            case CONFIRM_CARGO -> {
                checkSelection(userData);
                Role role = userData.getRole();
                yield switch (role) {
                    case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
                    case CLIENT, COURIER -> FORM;
                    case REQUESTS_VIEWER -> REQUEST_EDITOR;
                };
            }
            case SELECT_DOCUMENTS -> {
                userData.setDocumentsExist(true);
                yield currentState;
            }
            case CANCEL_DOCUMENTS -> {
                userData.setDocumentsExist(false);
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

    private void checkSelection(UserData userData) {
        State currentState = userData.getStateHistory().peek();
        String callbackQueryId = userData.getCallbackQueryId();
        if (!userData.isDocumentsExist() &&
                userData.getPackageSize() == null &&
                userData.getCompanionCount() == 0) throw new ConfirmationException(currentState, callbackQueryId);
    }
}
