package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.State.*;

@Service
public class DateStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        String[] split = callback.split(":");
        var command = BotButton.Calendar.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        switch (command) {
            case CHANGE_MONTH -> {
                if (currentState == DATE_FROM) userData.setCurrentState(MONTH_FROM);
                else userData.setCurrentState(MONTH_TO);
            }
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                if (currentState == DATE_FROM) userData.setDateFrom(parsedDate);
                else userData.setDateTo(parsedDate);
                State nextState = userData.getStateFlow().getNextState(currentState);
                userData.setCurrentState(nextState);
            }
        }
    }
}
