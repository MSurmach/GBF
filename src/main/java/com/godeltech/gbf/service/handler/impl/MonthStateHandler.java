package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.CalendarBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class MonthStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallback();
        String[] split = callback.split(":");
        var clickedButton = CalendarBotButton.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        return switch (clickedButton) {
            case SELECT_MONTH -> currentState == MONTH_TO ? DATE_TO : DATE_FROM;
            case CHANGE_YEAR -> currentState == MONTH_TO ? YEAR_TO : YEAR_FROM;
            default -> currentState;
        };
    }
}
