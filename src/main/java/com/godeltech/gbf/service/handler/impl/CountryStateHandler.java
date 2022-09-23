package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CountryKeyboard;
import org.springframework.stereotype.Service;

@Service
public class CountryStateHandler extends LocaleBotStateHandler {

    public CountryStateHandler(LocalMessageSource localMessageSource, CountryKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        if (currentState == State.COUNTRY_FROM) userData.setCountryFrom(callback);
        else {
            userData.setCountryTo(callback);
            userData.setPreviousState(currentState);
        }
        StateFlow stateFlow = userData.getStateFlow();
        State nextState = stateFlow.getNextState(currentState);
        userData.setCurrentState(nextState);
        return callback;
    }
}
