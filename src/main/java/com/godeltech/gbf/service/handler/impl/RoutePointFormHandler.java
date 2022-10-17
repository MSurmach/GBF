package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RoutePointFormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.validator.RoutePointValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static com.godeltech.gbf.model.State.*;
import static com.godeltech.gbf.model.db.Status.*;

@Service
@AllArgsConstructor
public class RoutePointFormHandler implements Handler {

    private RoutePointValidator routePointValidator;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        var clicked = RoutePointFormButton.valueOf(callback);
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        return switch (clicked) {
            case ADD_COUNTRY, EDIT_COUNTRY -> COUNTRY;
            case ADD_CITY -> {
                routePointValidator.checkCountryIsNull(tempRoutePoint.getCountry(), userData.getCallbackQueryId());
                yield CITY;
            }
            case EDIT_CITY -> CITY;
            case ADD_VISIT_DATE, EDIT_VISIT_DATE -> DATE;
            case DELETE_CITY -> {
                tempRoutePoint.setCity(null);
                yield userData.getStateHistory().peek();
            }
            case DELETE_VISIT_DATE -> {
                tempRoutePoint.setVisitDate(null);
                yield userData.getStateHistory().peek();
            }
            case SAVE -> {
                routePointValidator.checkAllNecessaryData(tempRoutePoint, userData.getRole(), userData.getCallbackQueryId());
                LinkedList<RoutePoint> points = userData.getRoutePoints();
                yield switch (tempRoutePoint.getStatus()) {
                    case INITIAL -> {
                        if (points.stream().anyMatch(point -> point.getStatus() == INITIAL))
                            points.removeFirst();
                        points.addFirst(tempRoutePoint);
                        yield FORM;
                    }
                    case INTERMEDIATE -> {
                        int intermediateOrder = tempRoutePoint.getOrderNumber();
                        if (points.isEmpty() ||
                                (points.size() == 1 && points.getFirst().getStatus() == FINAL))
                            points.addFirst(tempRoutePoint);
                        else {
                            if (points.get(intermediateOrder).getStatus() == INTERMEDIATE)
                                points.remove(intermediateOrder);
                            points.add(intermediateOrder, tempRoutePoint);
                        }
                        yield INTERMEDIATE_EDITOR;
                    }
                    case FINAL -> {
                        if (points.stream().anyMatch(point -> point.getStatus() == FINAL))
                            points.removeLast();
                        points.addLast(tempRoutePoint);
                        yield FORM;
                    }
                };
            }
        };
    }
}
