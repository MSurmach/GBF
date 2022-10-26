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
public class RequestsMessageType implements MessageType, PaginationInfo<TelegramUser> {
    private LocalMessageSource lms;
    private DetailsCreator detailsCreator;
    public final static String REQUESTS_EXIST_INITIAL = "requests.exist.initial";
    public final static String REQUESTS_NOT_EXIST_INITIAL = "requests.notExist.initial";
    public final static String REQUESTS_DATA_ID = "request.data.id";

    @Override
    public State getState() {
        return State.REQUESTS;
    }

    @Override
    public String getMessage(UserData userData) {
        String recordIdHeader = lms.getLocaleMessage(REQUESTS_DATA_ID, userData.getId().toString());
        return recordIdHeader + detailsCreator.createAllDetails(userData);
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
