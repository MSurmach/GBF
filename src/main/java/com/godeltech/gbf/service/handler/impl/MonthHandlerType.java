package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DATE;
import static com.godeltech.gbf.model.State.YEAR;

@Service
public class MonthHandlerType implements HandlerType {

    @Override
    public State getState() {
        return State.MONTH;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clickedButton = CalendarBotButton.valueOf(split[0]);
        return switch (clickedButton) {
            case SELECT_MONTH -> DATE;
            case CHANGE_YEAR -> YEAR;
            default -> rollbackCallbackAndStateHistory(session);
        };
    }

    private State rollbackCallbackAndStateHistory(Session session) {
        session.getCallbackHistory().remove(1);
        return session.getStateHistory().pop();
    }
}
