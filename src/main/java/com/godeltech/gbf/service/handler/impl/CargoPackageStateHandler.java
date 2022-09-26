package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.keyboard.impl.CargoPackageKeyboard;
import org.springframework.stereotype.Service;

@Service
public class CargoPackageStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        String callback = userData.getCallback();
        userData.setPackageSize(callback);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(State.CARGO_MENU);
    }
}
