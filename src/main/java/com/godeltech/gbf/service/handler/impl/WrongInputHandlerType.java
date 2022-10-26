package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.WRONG_INPUT;

@Service
public class WrongInputHandlerType implements HandlerType {

    @Override
    public State getState() {
        return WRONG_INPUT;
    }

    @Override
    public State handle(UserData userData) {
        return WRONG_INPUT;
    }
}
