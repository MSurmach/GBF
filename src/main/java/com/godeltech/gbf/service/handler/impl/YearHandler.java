package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MONTH;

@Service
public class YearHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        return MONTH;
    }
}
