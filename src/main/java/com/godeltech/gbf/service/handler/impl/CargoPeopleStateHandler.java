package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.CARGO_MENU;

@Service
public class CargoPeopleStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        userData.setCompanionCount(Integer.parseInt(callback));
        return CARGO_MENU;
    }
}
