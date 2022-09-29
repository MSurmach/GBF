package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class AlertStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        State previousState = userData.getPreviousState();
        userData.setCurrentState(previousState);
    }
}
