package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class RouteMessageType implements MessageType {
    public final static String ROUTE_INFO_ABOUT = "route.info.about";
    public final static String ROUTE_INFO_EMPTY_CODE = "route.info.empty";
    public final static String ROUTE_INFO_NOT_EMPTY_CODE = "route.info.not.empty";
    public final static String ROUTE_QUESTION_INITIAL_POINT = "route.question.initialPoint";
    public final static String ROUTE_QUESTION_NEXT_POINT = "route.question.nextPoint";
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.ROUTE;
    }

    @Override
    public String getMessage(UserData userData) {
        LinkedList<RoutePoint> tempRoute = userData.getTempRoute();
        String about = lms.getLocaleMessage(ROUTE_INFO_ABOUT, userData.getUsername());
        String route = tempRoute.isEmpty() ?
                lms.getLocaleMessage(ROUTE_INFO_EMPTY_CODE) :
                lms.getLocaleMessage(ROUTE_INFO_NOT_EMPTY_CODE, routeAsString(tempRoute));
        String question = tempRoute.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.INITIAL) ?
                lms.getLocaleMessage(ROUTE_QUESTION_NEXT_POINT) :
                lms.getLocaleMessage(ROUTE_QUESTION_INITIAL_POINT);
        return about + route + question;
    }

    private String routeAsString(List<RoutePoint> route) {
        final String arrow = " ➜ ";
        var stringBuilder = new StringBuilder();
        for (int index = 1; index <= route.size(); index++) {
            RoutePoint routePoint = route.get(index - 1);
            String localCityName = lms.getLocaleMessage(routePoint.getCity().getName());
            String cityStatusImage = getStatusImage(routePoint);
            stringBuilder.append(cityStatusImage).append(" ").append(localCityName);
            if (index != route.size())
                stringBuilder.append(arrow);
        }
        return stringBuilder.toString();
    }

    private String getStatusImage(RoutePoint routePoint) {
        return switch (routePoint.getStatus()) {
            case INITIAL -> "\uD83D\uDEA9";
            case INTERMEDIATE -> lms.getLocaleMessage(String.valueOf(routePoint.getOrderNumber()));
            case FINAL -> "\uD83C\uDFC1";
        };
    }
}
