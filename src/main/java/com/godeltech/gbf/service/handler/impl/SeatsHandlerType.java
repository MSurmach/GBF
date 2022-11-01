package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.FORM;

@Service
public class SeatsHandlerType implements HandlerType {

    @Override
    public State getState() {
        return State.SEATS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        try {
            int count = Integer.parseInt(callback);
            sessionData.setSeats(count);
            return FORM;
        } catch (NumberFormatException numberFormatException) {
            return sessionData.getStateHistory().pop();
        }
    }
}
