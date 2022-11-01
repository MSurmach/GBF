package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.MessageUtils;
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
        String routeDetails = MessageUtils.routeDetails(tempRoute, lms);
        String question = tempRoute.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.INITIAL) ?
                lms.getLocaleMessage(ROUTE_QUESTION_NEXT_POINT) :
                lms.getLocaleMessage(ROUTE_QUESTION_INITIAL_POINT);
        return about + routeDetails + question;
    }
}
