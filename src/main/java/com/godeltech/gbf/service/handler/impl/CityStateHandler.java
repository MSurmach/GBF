package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.CalendarCommand;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CityKeyboard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CityStateHandler extends LocaleBotStateHandler {

    public CityStateHandler(LocaleMessageSource localeMessageSource, CityKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        rememberState(currentState, userData, callback);
        if (currentState == State.CITY_TO) userData.setCityTo(callback);
        else userData.setCityFrom(callback);
        StateFlow stateFlow = userData.getStateFlow();
        userData.setCurrentState(stateFlow.getNextState(currentState));
        return CalendarCommand.INIT + ":" + LocalDate.now();
    }
}
