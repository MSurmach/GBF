package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CountryKeyboard;
import org.springframework.stereotype.Service;

@Service
public class CountryStateHandler extends LocaleBotStateHandler {

    public CountryStateHandler(LocaleMessageSource localeMessageSource, CountryKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        rememberState(currentState, userData, callback);
        if (currentState == State.COUNTRY_TO) userData.setCountryTo(callback);
        else userData.setCountryFrom(callback);
        StateFlow stateFlow = userData.getStateFlow();
        userData.setCurrentState(stateFlow.getNextState(currentState));
        return callback;
    }
}
