package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DELIVERY;

@Service
public class CargoPackageHandlerType implements HandlerType {

    @Override
    public State getState() {
        return DELIVERY;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        userData.setDeliverySize(Integer.parseInt(callback));
        return DELIVERY;
    }
}
