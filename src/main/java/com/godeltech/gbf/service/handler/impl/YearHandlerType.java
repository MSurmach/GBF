package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
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
    public State handle(Session session) {
        return MONTH;
    }
}
