package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controls.Command;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.controls.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CargoKeyboard;
import org.springframework.stereotype.Service;

@Service
public class CargoStateHandler extends LocaleBotStateHandler {

    public CargoStateHandler(LocalMessageSource localMessageSource, CargoKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        var command = Command.Cargo.valueOf(callback);
        switch (command) {
            case CONFIRM_CARGO -> {
                StateFlow stateFlow = userData.getStateFlow();
                userData.setCurrentState(stateFlow.getNextState(currentState));
            }
        }
        return callback;
    }
}
