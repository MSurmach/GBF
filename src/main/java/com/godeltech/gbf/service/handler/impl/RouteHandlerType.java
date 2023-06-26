package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RouteButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.service.city.CityService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.RouteValidator;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RouteHandlerType implements HandlerType {

    private CityService cityService;
    private RouteValidator routeValidator;

    @Override
    public State getState() {
        return State.ROUTE;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clicked = RouteButton.valueOf(split[0]);
        LinkedList<RoutePoint> tempRoute = session.getTempRoute();
        switch (clicked) {
            case SELECT_CITY -> {
                String cityName = split[1];
                var city = cityService.findCityByName(cityName);
                Optional<RoutePoint> routePointOptional = tempRoute.stream().filter(routePoint -> routePoint.getCity().equals(city)).findFirst();
                if (routePointOptional.isPresent()) tempRoute.remove(routePointOptional.get());
                else tempRoute.add(new RoutePoint(city));
                normalizeRoutePointsOrders(tempRoute);
            }
            case CONFIRM_ROUTE -> {
                    routeValidator.checkRouteHasMoreOrEqualsThan2Points(tempRoute, session.getCallbackQueryId(), session.getTelegramUser().getLanguage());

                session.setRoute(new LinkedList<>(tempRoute));
                tempRoute.clear();
            }
            case CLEAR_ROUTE -> tempRoute.clear();
        };
        return clicked.getNextState();
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
