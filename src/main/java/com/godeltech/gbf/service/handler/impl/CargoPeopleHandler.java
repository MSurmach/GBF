package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.CARGO_MENU;

@Service
public class CargoPeopleHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        try {
            int count = Integer.parseInt(callback);
            userData.setCompanionCount(count);
            return CARGO_MENU;
        } catch (NumberFormatException numberFormatException) {
            return userData.getStateHistory().peek();
        }
    }
}
