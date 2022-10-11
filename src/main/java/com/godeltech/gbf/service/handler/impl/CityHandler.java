package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

@Service
public class CityHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        City city = new City(callback);
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        tempRoutePoint.setCity(city);
        return userData.getStateHistory().peek();
    }
}
