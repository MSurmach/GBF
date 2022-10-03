package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.WRONG_INPUT;

@Service
public class WrongInputStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        return WRONG_INPUT;
    }
}
