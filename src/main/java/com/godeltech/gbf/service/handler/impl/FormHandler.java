package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ROUTE_POINT_FORM;
import static com.godeltech.gbf.model.State.SUCCESS;
import static com.godeltech.gbf.model.db.Status.*;

@Service
public class FormHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        var callback = userData.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        return switch (clicked) {
            case ADD_INITIAL_ROUTE_POINT -> handleAddRoutePoint(userData, INITIAL);
            case ADD_FINAL_ROUTE_POINT -> handleAddRoutePoint(userData, FINAL);
            case ADD_INTERMEDIATE_ROUTE_POINT -> handleAddRoutePoint(userData, INTERMEDIATE);
            case EDIT_INITIAL_ROUTE_POINT -> handleEditRoutePoint(userData, INITIAL);
            case EDIT_FINAL_ROUTE_POINT -> handleEditRoutePoint(userData, FINAL);
            case EDIT_INTERMEDIATE_ROUTE_POINT -> handleEditRoutePoint(userData, INTERMEDIATE);
            case ADD_COMMENT, EDIT_COMMENT -> State.COMMENT;
            case DELETE_COMMENT -> {
                userData.setComment(null);
                yield userData.getStateHistory().peek();
            }
            case ADD_CARGO, EDIT_CARGO -> State.CARGO_MENU;
            case FORM_REGISTER -> SUCCESS;
        };
    }

    private State handleAddRoutePoint(UserData userData, Status status) {
        var toAdd = new RoutePoint();
        toAdd.setStatus(status);
        userData.setTempRoutePoint(toAdd);
        return ROUTE_POINT_FORM;
    }

    private State handleEditRoutePoint(UserData userData, Status status) {
        var toEdit = userData.getRoutePoints().stream().
                filter(routePoint -> routePoint.getStatus() == status).
                findFirst().
                get();
        userData.setTempRoutePoint(toEdit);
        return ROUTE_POINT_FORM;
    }
}
