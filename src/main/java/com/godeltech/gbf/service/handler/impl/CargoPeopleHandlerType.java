package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DELIVERY;

@Service
public class CargoPeopleHandlerType implements HandlerType {

    @Override
    public State getState() {
        return State.SEATS;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        try {
            int count = Integer.parseInt(callback);
            userData.setSeats(count);
            return DELIVERY;
        } catch (NumberFormatException numberFormatException) {
            return userData.getStateHistory().peek();
        }
    }
}
