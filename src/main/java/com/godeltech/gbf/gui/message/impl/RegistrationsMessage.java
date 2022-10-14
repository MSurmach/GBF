package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationsMessage implements Message, PaginationInfo<TelegramUser> {
    private LocalMessageSource lms;
    private DetailsCreator detailsCreator;
    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATION_DATA_ID = "registration.data.id";


    @Override
    public String getMessage(UserData userData) {
        String registrationWithIdRow = lms.getLocaleMessage(REGISTRATION_DATA_ID, userData.getUserId().toString());
        return registrationWithIdRow +
                detailsCreator.createAllDetails(userData);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> pages = userData.getPage();
        String username = userData.getUsername();
        return (pages != null && !pages.isEmpty()) ?
                lms.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) +
                        paginationInfoLocalMessage(pages, lms) :
                lms.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username);
    }
}
