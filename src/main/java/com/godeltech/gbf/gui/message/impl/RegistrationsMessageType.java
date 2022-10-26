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

@Service
@AllArgsConstructor
public class RegistrationsMessageType implements MessageType, PaginationInfo<TelegramUser> {
    private LocalMessageSource lms;
    private DetailsCreator detailsCreator;
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_DATA_ID = "registration.data.id";


    @Override
    public State getState() {
        return State.REGISTRATIONS;
    }

    @Override
    public String getMessage(UserData userData) {
        String registrationWithIdRow = lms.getLocaleMessage(REGISTRATION_DATA_ID, userData.getId().toString());
        return registrationWithIdRow +
                detailsCreator.createAllDetails(userData);
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
