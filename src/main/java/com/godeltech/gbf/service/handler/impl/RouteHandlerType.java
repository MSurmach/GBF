package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RouteButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.service.city.CityService;
import com.godeltech.gbf.service.handler.HandlerType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static com.godeltech.gbf.model.State.FORM;
import static com.godeltech.gbf.model.State.ROUTE;

@Service
@AllArgsConstructor
public class RouteHandlerType implements HandlerType {

    private CityService cityService;

    @Override
    public State getState() {
        return State.ROUTE;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clicked = RouteButton.valueOf(split[0]);
        String cityName = split[1];
        return switch (clicked) {
            case SELECT_CITY -> {
                City city = cityService.findCityByName(cityName);
                LinkedList<RoutePoint> tempRoute = userData.getTempRoute();
                if (!tempRoute.removeFirstOccurrence(city))
                    tempRoute.add(new RoutePoint(city));
                normalizeRoutePointsOrders(tempRoute);
                yield ROUTE;
            }
            case CONFIRM_ROUTE -> {
                LinkedList<RoutePoint> tempRoute = userData.getTempRoute();
                userData.setRoute(tempRoute);
                tempRoute.clear();
                yield FORM;
            }
        };
    }

    private void normalizeRoutePointsOrders(LinkedList<RoutePoint> route) {
        if (route.isEmpty()) return;
        int routeSize = route.size();
        for (int index = 0; index < routeSize; index++) {
            RoutePoint routePoint = route.get(index);
            routePoint.setOrderNumber(index);
            if (index == 0) {
                routePoint.setStatus(Status.INITIAL);
                continue;
            }
            if (index == routeSize - 1) {
                routePoint.setStatus(Status.FINAL);
                continue;
            }
            routePoint.setStatus(Status.INTERMEDIATE);
        }
    }
}
