package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.gui.message.MessageUtils.*;

@Service
@AllArgsConstructor
public class RequestsMessageType implements MessageType, PaginationInfo<TelegramUser> {
    public final static String REQUESTS_EXIST_INITIAL = "requests.exist.initial";
    public final static String REQUESTS_NOT_EXIST_INITIAL = "requests.notExist.initial";
    public final static String REQUESTS_DATA_ID = "request.data.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REQUESTS;
    }

    @Override
    public String getMessage(UserData userData) {
        return lms.getLocaleMessage(REQUESTS_DATA_ID, userData.getId().toString()) +
                routeDetails(userData.getRoute(), lms) +
                datesDetails(userData.getStartDate(), userData.getEndDate(), lms) +
                deliveryDetails(userData.getDeliverySize(), lms) +
                seatsDetails(userData.getSeats(), lms) +
                commentDetails(userData.getComment(), lms);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> pages = userData.getPage();
        String username = userData.getUsername();
        return (pages != null && !pages.isEmpty()) ?
                lms.getLocaleMessage(REQUESTS_EXIST_INITIAL, username) +
                        paginationInfoLocalMessage(pages, lms) :
                lms.getLocaleMessage(REQUESTS_NOT_EXIST_INITIAL, username);
    }
}
