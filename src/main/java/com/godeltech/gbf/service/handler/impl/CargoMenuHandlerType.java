package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CargoBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.CargoValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class CargoMenuHandlerType implements HandlerType {

    private CargoValidator cargoValidator;

    @Override
    public State getState() {
        return CARGO_MENU;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        var clickedButton = CargoBotButton.valueOf(callback);
        return switch (clickedButton) {
            case CONFIRM_CARGO -> {
                cargoValidator.checkIfCargoIsEmpty(userData);
                yield FORM;
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
                userData.setPackageSize(0);
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
