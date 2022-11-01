package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MONTH;

@Service
public class YearHandlerType implements HandlerType {

    @Override
    public State getState() {
        return State.YEAR;
    }

    @Override
    public State handle(SessionData sessionData) {
        return MONTH;
    }
}
