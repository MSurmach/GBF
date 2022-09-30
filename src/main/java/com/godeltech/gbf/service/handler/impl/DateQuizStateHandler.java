package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.button.BotButton.Calendar.INIT;
import static com.godeltech.gbf.model.State.*;

@Service
public class DateQuizStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        State currentState = userData.getCurrentState();
        BotButton.DateQuiz clicked = BotButton.DateQuiz.valueOf(callback);
        State nextState = switch (clicked) {
            case SELECT_DATE -> currentState == DATE_TO_QUIZ ? DATE_TO : DATE_FROM;
            case SKIP_DATE -> currentState == DATE_TO_QUIZ ? CARGO_MENU : COUNTRY_TO;
        };
        userData.setCallback(INIT + ":" + LocalDate.now());
        userData.setCurrentState(nextState);
    }
}
