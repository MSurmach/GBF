package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.service.validator.FormValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.godeltech.gbf.model.State.*;
import static com.godeltech.gbf.model.db.Status.*;

@Service
@AllArgsConstructor
public class FormHandlerType implements HandlerType {

    private UserService userService;
    private FormValidator formValidator;

    @Override
    public State getState() {
        return FORM;
    }

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
            case FORM_REGISTER, FORM_SEARCH, FORM_EDIT_CONFIRM -> {
                formValidator.validateBeforeSave(userData);
                yield saveAndGetAppropriateState(userData);
            }
        };
    }

    private State saveAndGetAppropriateState(UserData userData) {
        State nextState = switch (userData.getRole()) {
            case COURIER -> SUCCESS;
            case CLIENT -> {
                TelegramUser telegramUser = ModelUtils.telegramUser(userData);
                userData.setTempForSearch(telegramUser);
                yield COURIERS_LIST_RESULT;
            }
            case REGISTRATIONS_VIEWER -> REGISTRATIONS;
            case REQUESTS_VIEWER -> REQUESTS;
        };
        userService.save(userData);
        return nextState;
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
