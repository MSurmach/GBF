package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.IntermediateEditorButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import com.godeltech.gbf.service.handler.Handler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class IntermediateEditorHandler implements Handler {
    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clicked = IntermediateEditorButton.valueOf(splittedCallback[0]);
        LinkedList<RoutePoint> points = userData.getRoutePoints();
        return switch (clicked) {
            case ORDER_UP -> {
                var order = Integer.valueOf(splittedCallback[1]);
                moveUpRoutePoint(points, order);
                yield INTERMEDIATE_EDITOR;
            }
            case ORDER_DOWN -> {
                var order = Integer.valueOf(splittedCallback[1]);
                moveDownRoutePoint(points, order);
                yield INTERMEDIATE_EDITOR;
            }
            case DELETE_ROUTE_POINT -> {
                //points.remove(currentIndex);
                yield INTERMEDIATE_EDITOR;
            }
            case EDIT_INTERMEDIATE_ROUTE_POINT -> {
                //userData.setTempRoutePoint(points.get(order - 1));
                yield ROUTE_POINT_FORM;
            }
            case SAVE_CHANGES -> FORM;
        };
    }

    private void moveUpRoutePoint(List<RoutePoint> points, int order) {
        int currentIndex = order;
        int previousIndex = currentIndex - 1;
        if (previousIndex < 0) return;
        if (points.get(previousIndex).getStatus() != Status.INITIAL) {
            RoutePoint currentRoutePoint = points.get(currentIndex);
            RoutePoint previousRoutePoint = points.get(previousIndex);
            points.set(currentIndex, previousRoutePoint);
            points.set(previousIndex, currentRoutePoint);
            currentRoutePoint.setOrderNumber(previousRoutePoint.getOrderNumber());
            previousRoutePoint.setOrderNumber(order);
        }
    }

    private void moveDownRoutePoint(List<RoutePoint> points, int order) {
        int currentIndex = order;
        int nextIndex = currentIndex + 1;
        if (nextIndex > points.size() - 1) return;
        if (points.get(nextIndex).getStatus() != Status.FINAL) {
            RoutePoint currentRoutePoint = points.get(currentIndex);
            RoutePoint nextRoutePoint = points.get(nextIndex);
            points.set(currentIndex, nextRoutePoint);
            points.set(nextIndex, currentRoutePoint);
            currentRoutePoint.setOrderNumber(nextRoutePoint.getOrderNumber());
            nextRoutePoint.setOrderNumber(order);
        }
    }
}
