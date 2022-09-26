package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CityKeyboard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.button.BotButton.Calendar.INIT;
import static com.godeltech.gbf.management.State.CITY_TO;

@Service
public class CityStateHandler extends LocaleBotStateHandler {

    public CityStateHandler(LocalMessageSource localMessageSource, CityKeyboard keyboard, LocalAnswer localBotMessage) {
        super(localMessageSource, keyboard, localBotMessage);
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
