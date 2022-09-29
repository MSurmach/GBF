package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class MonthStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        String[] split = callback.split(":");
        var command = BotButton.Calendar.valueOf(split[0]);
        State currentState = userData.getCurrentState();
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
