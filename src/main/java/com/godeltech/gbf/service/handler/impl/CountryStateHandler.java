package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CountryStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        if (currentState == COUNTRY_FROM) {
            userData.setCountryFrom(callback);
            return CITY_FROM;
        } else {
            userData.setCountryTo(callback);
            return CITY_TO;
        }
    }
}
