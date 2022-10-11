package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
public class CountryHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        State currentState = userData.getStateHistory().peek();
        RoutePoint routePoint = new RoutePoint(new Country(callback));
        userData.getRoutePoints().addLast(routePoint);
        if (currentState == COUNTRY_FROM) {
            userData.setCountryFrom(callback);
            return CITY_FROM;
        } else {
            userData.setCountryTo(callback);
            return CITY_TO;
        }
    }
}
