package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CityButton;
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

import static com.godeltech.gbf.model.State.CITY;
import static com.godeltech.gbf.model.State.FORM;

@Service
@AllArgsConstructor
public class CityHandlerType implements HandlerType {

    private CityService cityService;

    @Override
    public State getState() {
        return State.CITY;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clicked = CityButton.valueOf(split[0]);
        String cityName = split[1];
        return switch (clicked) {
            case SELECT_CITY -> {
                City city = cityService.findCityByName(cityName);
                LinkedList<RoutePoint> routePoints = userData.getRoutePoints();
                if (!isCityExistInRoute(city, routePoints))
                    changeRoute(city, routePoints);
                normalizeRoutePointsOrders(routePoints);
                yield CITY;
            }
            case CONFIRM_ROUTE -> FORM;
        };
    }

    private void changeRoute(City city, LinkedList<RoutePoint> routePoints) {
        if (routePoints.isEmpty() || routePoints.getFirst().getStatus() != Status.INITIAL) {
            routePoints.addFirst(new RoutePoint(Status.INITIAL, city));
            return;
        }
        if (routePoints.getLast().getStatus() != Status.FINAL) {
            routePoints.addLast(new RoutePoint(Status.FINAL, city));
            return;
        }
        routePoints.add(routePoints.size() - 2, new RoutePoint(Status.INTERMEDIATE, city));
    }

    private void normalizeRoutePointsOrders(LinkedList<RoutePoint> routePoints) {
        for (int index = 0; index < routePoints.size(); index++)
            routePoints.get(index).setOrderNumber(index);
    }

    private boolean isCityExistInRoute(City city, LinkedList<RoutePoint> routePoints) {
        for (int index = 0; index < routePoints.size(); index++) {
            if (routePoints.get(index).getCity().equals(city)) {
                routePoints.remove(index);
                return true;
            }
        }
        return false;
    }
}
