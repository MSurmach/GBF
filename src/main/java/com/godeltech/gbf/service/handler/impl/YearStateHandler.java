package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.YearKeyboard;
import org.springframework.stereotype.Service;

@Service
public class YearStateHandler extends LocaleBotStateHandler {


    public YearStateHandler(LocaleMessageSource localeMessageSource, YearKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        String[] split = callback.split(":");
        String callbackDate = split[1];
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        if (currentState == State.YEAR_TO) userData.setCurrentState(State.MONTH_TO);
        else userData.setCurrentState(State.MONTH_FROM);
        return callback;
    }
}
