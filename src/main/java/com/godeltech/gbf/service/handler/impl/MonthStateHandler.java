package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.CalendarCommand;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.MonthKeyboard;
import org.springframework.stereotype.Service;

@Service
public class MonthStateHandler extends LocaleBotStateHandler {

    public MonthStateHandler(LocaleMessageSource localeMessageSource, MonthKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        String[] split = callback.split(":");
        CalendarCommand callbackCommand = CalendarCommand.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        switch (callbackCommand) {
            case RETURNEDMONTH -> {
                if (currentState == State.MONTH_TO) userData.setCurrentState(State.DATE_TO);
                else userData.setCurrentState(State.DATE_FROM);
            }
            case YEAR -> {
                if (currentState == State.MONTH_TO) userData.setCurrentState(State.YEAR_TO);
                else userData.setCurrentState(State.YEAR_FROM);
            }
        }
        return callback;
    }
}
