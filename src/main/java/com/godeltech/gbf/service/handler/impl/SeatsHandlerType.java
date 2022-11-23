package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;
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
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        try {
            int count = Integer.parseInt(callback);
            session.setSeats(count);
            return FORM;
        } catch (NumberFormatException numberFormatException) {
            return session.getStateHistory().pop();
        }
    }
}
