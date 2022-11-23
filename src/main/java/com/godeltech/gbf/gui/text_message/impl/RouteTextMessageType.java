package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.gui.utils.MessageUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
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
    public String getMessage(Session session) {
        log.debug("Create route message type for session data with user: {}",
                session.getTelegramUser() );
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        LinkedList<RoutePoint> tempRoute = session.getTempRoute();
        String about = lms.getLocaleMessage(ROUTE_INFO_ABOUT, ModelUtils.getUserMention(session));
        String routeDetails = MessageUtils.routeDetails(tempRoute, lms);
        String question = tempRoute.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.INITIAL) ?
                lms.getLocaleMessage(ROUTE_QUESTION_NEXT_POINT) :
                lms.getLocaleMessage(ROUTE_QUESTION_INITIAL_POINT);
        return about + routeDetails + question;
    }
}
