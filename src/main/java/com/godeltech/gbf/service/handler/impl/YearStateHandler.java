package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.YearKeyboard;
import org.springframework.stereotype.Service;

@Service
public class YearStateHandler extends LocaleBotStateHandler {

    public YearStateHandler(LocalMessageSource localMessageSource, YearKeyboard keyboard, LocalAnswer localBotMessage) {
        super(localMessageSource, keyboard, localBotMessage);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        if (currentState == State.YEAR_TO) userData.setCurrentState(State.MONTH_TO);
        else userData.setCurrentState(State.MONTH_FROM);
        return callback;
    }
}
