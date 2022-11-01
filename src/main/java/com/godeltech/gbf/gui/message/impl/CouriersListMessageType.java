package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class CouriersListMessageType implements MessageType, PaginationInfo<TelegramUser> {
    public final static String COURIERS_LIST_INITIAL_EXIST = "couriers.list.initial.exist";
    public final static String COURIERS_LIST_INITIAL_NOT_EXIST = "couriers.list.initial.notExist";
    public final static String COURIERS_LIST_HEADER = "couriers.list.header";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.COURIERS_LIST_RESULT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(COURIERS_LIST_HEADER, sessionData.getUsername()) +
                routeDetails(sessionData.getRoute(), lms) +
                datesDetails(sessionData.getStartDate(), sessionData.getEndDate(), lms) +
                deliveryDetails(sessionData.getDelivery(), lms) +
                seatsDetails(sessionData.getSeats(), lms) +
                commentDetails(sessionData.getComment(), lms);
    }

    public String initialMessage(SessionData sessionData) {
        Page<TelegramUser> page = sessionData.getPage();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(COURIERS_LIST_INITIAL_EXIST, sessionData.getUsername()) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(COURIERS_LIST_INITIAL_NOT_EXIST, sessionData.getUsername());
    }
}
