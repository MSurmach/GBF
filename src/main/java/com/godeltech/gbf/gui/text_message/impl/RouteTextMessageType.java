package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.gui.utils.MessageUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

@Component
@AllArgsConstructor
@Slf4j
public class RouteTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.ROUTE;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create route message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        LinkedList<RoutePoint> tempRoute = sessionData.getTempRoute();
        String about = lms.getLocaleMessage(ROUTE_INFO_ABOUT, ModelUtils.getUserMention(sessionData));
        String routeDetails = MessageUtils.routeDetails(tempRoute, lms);
        String question = tempRoute.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.INITIAL) ?
                lms.getLocaleMessage(ROUTE_QUESTION_NEXT_POINT) :
                lms.getLocaleMessage(ROUTE_QUESTION_INITIAL_POINT);
        return about + routeDetails + question;
    }
}
