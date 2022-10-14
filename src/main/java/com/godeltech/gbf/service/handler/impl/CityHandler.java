package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ROUTE_POINT_FORM;

@Service
public class CityHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        String[] splittedCallback = callback.split(":");
        Integer cityId = Integer.valueOf(splittedCallback[1]);
        String cityName = splittedCallback[0];
        City city = new City(cityId, cityName, tempRoutePoint.getCountry());
        tempRoutePoint.setCity(city);
        return ROUTE_POINT_FORM;
    }
}
