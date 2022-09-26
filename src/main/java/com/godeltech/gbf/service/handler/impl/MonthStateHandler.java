package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.keyboard.impl.MonthKeyboard;
import org.springframework.stereotype.Service;

@Service
public class MonthStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        String[] split = callback.split(":");
        var command = BotButton.Calendar.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        switch (command) {
            case SELECT_MONTH -> {
                if (currentState == State.MONTH_TO) userData.setCurrentState(State.DATE_TO);
                else userData.setCurrentState(State.DATE_FROM);
            }
            case CHANGE_YEAR -> {
                if (currentState == State.MONTH_TO) userData.setCurrentState(State.YEAR_TO);
                else userData.setCurrentState(State.YEAR_FROM);
            }
        }
    }
}
