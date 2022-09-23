package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.controls.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CityKeyboard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.controls.Command.Calendar.INIT;
import static com.godeltech.gbf.controls.State.CITY_TO;

@Service
public class CityStateHandler extends LocaleBotStateHandler {

    public CityStateHandler(LocalMessageSource localMessageSource, CityKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        if (currentState == CITY_TO) userData.setCityTo(callback);
        else userData.setCityFrom(callback);
        StateFlow stateFlow = userData.getStateFlow();
        userData.setPreviousState(currentState);
        userData.setCurrentState(stateFlow.getNextState(currentState));
        return INIT + ":" + LocalDate.now();
    }
}
