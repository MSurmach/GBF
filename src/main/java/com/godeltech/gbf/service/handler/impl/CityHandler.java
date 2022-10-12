package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.city.CityService;
import com.godeltech.gbf.service.handler.Handler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ROUTE_POINT_FORM;

@Service
@AllArgsConstructor
public class CityHandler implements Handler {
    private CityService cityService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        City city = cityService.findCityByName(callback);
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        tempRoutePoint.setCity(city);
        return ROUTE_POINT_FORM;
    }
}
