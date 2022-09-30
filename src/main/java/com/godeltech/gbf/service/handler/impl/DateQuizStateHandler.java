package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class DateQuizStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        State currentState = userData.getCurrentState();
        BotButton.DateQuiz clicked = BotButton.DateQuiz.valueOf(callback);
        State nextState = switch (clicked) {
            case SELECT_DATE -> currentState == DATE_TO_QUIZ ? DATE_TO : DATE_FROM_QUIZ;
            case SKIP_DATE -> currentState == DATE_TO_QUIZ ? COUNTRY_TO : CARGO_MENU;
        };
        userData.setCurrentState(nextState);
    }
}
