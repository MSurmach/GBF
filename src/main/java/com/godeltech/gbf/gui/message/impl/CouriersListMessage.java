package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.MessageUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouriersListMessage implements Message, PaginationInfo<TelegramUser> {
    public final static String COURIERS_LIST_INITIAL_EXIST = "couriers.list.initial.exist";
    public final static String COURIERS_LIST_INITIAL_NOT_EXIST = "couriers.list.initial.notExist";
    public final static String COURIERS_LIST_HEADER = "couriers.list.header";
    private final DetailsCreator detailsCreator;
    private LocalMessageSource lms;

    @Override
    public String getMessage(UserData userData) {
        return lms.getLocaleMessage(COURIERS_LIST_HEADER, userData.getUsername()) +
                detailsCreator.createAllDetails(userData);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> page = userData.getPage();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(COURIERS_LIST_INITIAL_EXIST, userData.getUsername()) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(COURIERS_LIST_INITIAL_NOT_EXIST, userData.getUsername());
    }
}
