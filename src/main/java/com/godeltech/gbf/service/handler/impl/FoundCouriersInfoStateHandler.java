package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.COURIERS_LIST;

@Service
public class FoundCouriersInfoStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        return COURIERS_LIST;
    }
}
