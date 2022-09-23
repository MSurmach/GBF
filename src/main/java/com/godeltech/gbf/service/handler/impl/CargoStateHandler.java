package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CargoKeyboard;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.management.button.BotButton.IGNORE;

@Service
public class CargoStateHandler extends LocaleBotStateHandler {

    public CargoStateHandler(LocalMessageSource localMessageSource, CargoKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        var command = BotButton.Cargo.valueOf(callback);
        State currentState = userData.getCurrentState();
        return switch (command) {
            case CONFIRM_CARGO -> {
                StateFlow stateFlow = userData.getStateFlow();
                userData.setCurrentState(stateFlow.getNextState(currentState));
                yield IGNORE.name();
            }
            case SELECT_DOCUMENTS -> {
                userData.setDocuments(true);
                yield userId.toString();
            }
            case CANCEL_DOCUMENTS -> {
                userData.setDocuments(false);
                yield userId.toString();
            }
            case SELECT_PACKAGE, EDIT_PACKAGE -> {
                userData.setPreviousState(currentState);
                userData.setCurrentState(State.CARGO_PACKAGE);
                yield IGNORE.name();
            }
            case CANCEL_PACKAGE -> {
                userData.setPackageSize(null);
                yield userId.toString();
            }
            case SELECT_PEOPLE, EDIT_PEOPLE -> {
                userData.setPreviousState(currentState);
                userData.setCurrentState(State.CARGO_PEOPLE);
                yield IGNORE.name();
            }
            case CANCEL_PEOPLE -> {
                userData.setCompanionCount(0);
                yield userId.toString();
            }
            default -> IGNORE.name();
        };
    }
}
