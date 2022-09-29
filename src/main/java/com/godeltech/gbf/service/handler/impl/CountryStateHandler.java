package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CountryStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        State currentState = userData.getCurrentState();
        if (currentState == COUNTRY_FROM) {
            userData.setCountryFrom(callback);
            userData.setCurrentState(CITY_FROM);
        } else {
            userData.setCountryTo(callback);
            userData.setCurrentState(CITY_TO);
        }
    }
}
