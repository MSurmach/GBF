package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;
import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Component
@AllArgsConstructor
@Slf4j
public class ClientsListMessageType implements MessageType, PaginationInfo<Offer> {

    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.CLIENTS_LIST_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create client list message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return lms.getLocaleMessage(CLIENTS_LIST_HEADER, sessionData.getUsername()) + routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    public String initialMessage(SessionData sessionData) {
        Page<Offer> page = sessionData.getPage();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_EXIST, sessionData.getUsername()) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_NOT_EXIST, sessionData.getUsername());
    }
}
