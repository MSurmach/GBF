package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.CalendarCommand;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CalendarKeyboard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.DATE_FROM;

@Service
public class DateStateHandler extends LocaleBotStateHandler {

    public DateStateHandler(LocaleMessageSource localeMessageSource, CalendarKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        String[] split = callback.split(":");
        CalendarCommand callbackCommand = CalendarCommand.valueOf(split[0]);
        String callbackDate = split[1];
        State currentState = userData.getCurrentState();
        switch (callbackCommand) {
            case MONTH -> {
                userData.setPreviousState(currentState);
                if (currentState == DATE_FROM) userData.setCurrentState(State.MONTH_FROM);
                else userData.setCurrentState(State.MONTH_TO);
            }
            case DAY -> {
                LocalDate parsedDate = LocalDate.parse(callbackDate);
                if (currentState == DATE_FROM) {
                    userData.setDateFrom(parsedDate);
                    userData.setCurrentState(State.COUNTRY_TO);
                } else {
                    userData.setDateTo(parsedDate);
                    userData.setCurrentState(State.LOAD);
                }
            }
        }
        return callback;
    }
}
