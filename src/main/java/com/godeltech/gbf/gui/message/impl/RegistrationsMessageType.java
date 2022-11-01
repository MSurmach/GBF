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

import static com.godeltech.gbf.gui.utils.MessageUtils.*;

@Service
@AllArgsConstructor
public class RegistrationsMessageType implements MessageType, PaginationInfo<TelegramUser> {
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_DATA_ID = "registration.data.id";
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.REGISTRATIONS;
    }

    @Override
    public String getMessage(UserData userData) {
        return lms.getLocaleMessage(REGISTRATION_DATA_ID, userData.getId().toString()) +
                routeDetails(userData.getRoute(), lms) +
                datesDetails(userData.getStartDate(), userData.getEndDate(), lms) +
                deliveryDetails(userData.getDelivery(), lms) +
                seatsDetails(userData.getSeats(), lms) +
                commentDetails(userData.getComment(), lms);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> page = userData.getPage();
        String username = userData.getUsername();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username);
    }
}
