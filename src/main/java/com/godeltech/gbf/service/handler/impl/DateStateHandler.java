package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.DateKeyboard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.management.button.BotButton.IGNORE;
import static com.godeltech.gbf.management.State.*;

@Service
public class DateStateHandler extends LocaleBotStateHandler {

    public DateStateHandler(LocalMessageSource localMessageSource, DateKeyboard keyboard, LocalAnswer localBotMessage) {
        super(localMessageSource, keyboard, localBotMessage);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        String[] split = callback.split(":");
        var command = BotButton.Calendar.valueOf(split[0]);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        return switch (command) {
            case CHANGE_MONTH -> {
                if (currentState == DATE_FROM) userData.setCurrentState(MONTH_FROM);
                else userData.setCurrentState(MONTH_TO);
                yield callback;
            }
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                if (currentState == DATE_FROM) userData.setDateFrom(parsedDate);
                else userData.setDateTo(parsedDate);
                State nextState = userData.getStateFlow().getNextState(currentState);
                userData.setCurrentState(nextState);
                yield userId.toString();
            }
            default -> IGNORE.name();
        };
    }
}
