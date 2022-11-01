package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.message.MessageUtils.*;

@Component
@AllArgsConstructor
public class ClientsListMessageType implements MessageType, PaginationInfo<TelegramUser> {
    public final static String CLIENTS_LIST_INITIAL_EXIST = "clients.list.initial.exist";
    public final static String CLIENTS_LIST_INITIAL_NOT_EXIST = "clients.list.initial.notExist";
    public final static String CLIENTS_LIST_HEADER = "clients.list.header";

    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.CLIENTS_LIST_RESULT;
    }

    @Override
    public String getMessage(UserData userData) {
        return lms.getLocaleMessage(CLIENTS_LIST_HEADER, userData.getUsername()) + routeDetails(userData.getRoute(), lms) +
                datesDetails(userData.getStartDate(), userData.getEndDate(), lms) +
                deliveryDetails(userData.getDeliverySize(), lms) +
                seatsDetails(userData.getSeats(), lms) +
                commentDetails(userData.getComment(), lms);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> page = userData.getPage();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_EXIST, userData.getUsername()) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_NOT_EXIST, userData.getUsername());
    }
}
