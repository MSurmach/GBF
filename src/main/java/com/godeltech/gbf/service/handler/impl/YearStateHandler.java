package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MONTH_FROM;
import static com.godeltech.gbf.model.State.MONTH_TO;

@Service
public class YearStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        return userData.getCurrentState() == State.YEAR_TO ? MONTH_TO : MONTH_FROM;
    }
}
