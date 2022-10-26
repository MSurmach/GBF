package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.CARGO_MENU;

@Service
public class CargoPackageHandlerType implements HandlerType {

    @Override
    public State getState() {
        return State.CARGO_PACKAGE;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        userData.setPackageSize(Integer.parseInt(callback));
        return CARGO_MENU;
    }
}
