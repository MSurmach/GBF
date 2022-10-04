package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.DateQuizBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class DateQuizStateHandler implements StateHandler {
    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        var clickedButton = DateQuizBotButton.valueOf(callback);
        return switch (clickedButton) {
            case SELECT_DATE -> currentState == DATE_TO_QUIZ ? DATE_TO : DATE_FROM;
            case SKIP_DATE -> currentState == DATE_TO_QUIZ ? CARGO_MENU : COUNTRY_TO;
        };
    }
}
