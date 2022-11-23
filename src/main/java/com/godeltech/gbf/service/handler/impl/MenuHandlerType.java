package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.MenuButton;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.*;
import static com.godeltech.gbf.model.State.MENU;

@Service
public class MenuHandlerType implements HandlerType {

    @Override
    public State getState() {
        return MENU;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        MenuButton clickedButton = MenuButton.valueOf(callback);
        switch (clickedButton){
            case START_AS_COURIER -> session.setRole(COURIER);
            case START_AS_CLIENT -> session.setRole(CLIENT);
            case LOOK_AT_MY_REGISTRATIONS, LOOK_AT_ALL_REGISTRATIONS -> session.setRole(REGISTRATIONS_VIEWER);
            case LOOK_AT_MY_REQUESTS,LOOK_AT_ALL_REQUESTS -> session.setRole(REQUESTS_VIEWER);
        }
        return clickedButton.getNextState();
    }
}
