package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DELIVERY;
import static com.godeltech.gbf.model.State.FORM;

@Service
public class DeliveryHandlerType implements HandlerType {

    @Override
    public State getState() {
        return DELIVERY;
    }


    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        sessionData.setDelivery(Delivery.valueOf(callback));
        return FORM;
    }
}
