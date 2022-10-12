package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DATE;
import static com.godeltech.gbf.model.State.YEAR;

@Service
public class MonthHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clickedButton = CalendarBotButton.valueOf(split[0]);
        return switch (clickedButton) {
            case SELECT_MONTH -> DATE;
            case CHANGE_YEAR -> YEAR;
            default -> rollbackCallbackAndStateHistory(userData);
        };
    }

    private State rollbackCallbackAndStateHistory(UserData userData) {
        userData.getCallbackHistory().remove(1);
        return userData.getStateHistory().pop();
    }
}
