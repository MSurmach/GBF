package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RoutePointFormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.Handler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RoutePointFormHandler implements Handler {
    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        var clicked = RoutePointFormButton.valueOf(callback);
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        return switch (clicked) {
            case ADD_COUNTRY, EDIT_COUNTRY -> COUNTRY_FROM;
            case ADD_CITY, EDIT_CITY -> CITY_FROM;
            case ADD_VISIT_DATE, EDIT_VISIT_DATE -> DATE_FROM;
            case DELETE_CITY -> {
                tempRoutePoint.setCity(null);
                yield userData.getStateHistory().peek();
            }
            case DELETE_VISIT_DATE -> {
                tempRoutePoint.setVisitDate(null);
                yield userData.getStateHistory().peek();
            }
        };
    }
}
