package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.godeltech.gbf.model.State.*;
import static com.godeltech.gbf.model.db.Status.*;

@Service
public class FormHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        var callback = userData.getCallbackHistory().peek();
        var clicked = FormButton.valueOf(callback);
        return switch (clicked) {
            case ADD_INITIAL_ROUTE_POINT -> {
                handleAddRoutePoint(userData, INITIAL, 0);
                yield ROUTE_POINT_FORM;
            }
            case EDIT_INITIAL_ROUTE_POINT -> {
                handleEditRoutePoint(userData, INITIAL);
                yield ROUTE_POINT_FORM;
            }
            case ADD_FINAL_ROUTE_POINT -> {
                handleAddRoutePoint(userData, FINAL, 0);
                yield ROUTE_POINT_FORM;
            }
            case INTERMEDIATE_EDITOR -> INTERMEDIATE_EDITOR;
            case ADD_INTERMEDIATE_ROUTE_POINT -> {
                int order = whichIntermediateRoutePointIndex(userData.getRoutePoints());
                handleAddRoutePoint(userData, INTERMEDIATE, order);
                yield ROUTE_POINT_FORM;
            }
            case EDIT_FINAL_ROUTE_POINT -> handleEditRoutePoint(userData, FINAL);
            case ADD_COMMENT, EDIT_COMMENT -> COMMENT;
            case DELETE_COMMENT -> {
                userData.setComment(null);
                yield userData.getStateHistory().peek();
            }
            case ADD_CARGO, EDIT_CARGO -> CARGO_MENU;
            case FORM_REGISTER -> {

                yield SUCCESS;
            }
            case FORM_SEARCH -> COURIERS_LIST;
        };
    }

    private void handleAddRoutePoint(UserData userData, Status status, int order) {
        var toAdd = new RoutePoint();
        toAdd.setStatus(status);
        toAdd.setOrderNumber(order);
        userData.setTempRoutePoint(toAdd);
    }

    private State handleEditRoutePoint(UserData userData, Status status) {
        var toEdit = getRoutePointByStatus(userData.getRoutePoints(), status);
        userData.setTempRoutePoint(toEdit);
        return ROUTE_POINT_FORM;
    }

    private void deleteRoutePointByStatus(List<RoutePoint> points, Status status) {
        RoutePoint toDelete = getRoutePointByStatus(points, status);
        points.remove(toDelete);
    }

    private RoutePoint getRoutePointByStatus(List<RoutePoint> points, Status status) {
        return points.stream().
                filter(routePoint -> routePoint.getStatus() == status).
                findFirst().
                get();
    }

    private int whichIntermediateRoutePointIndex(List<RoutePoint> points) {
        if (points.isEmpty()) return 1;
        int intermediateRoutePointCount = (int) points.stream().filter(point -> point.getStatus() == INTERMEDIATE).count();
        return intermediateRoutePointCount + 1;
    }
}
