package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.gui.message.PaginationInfo;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientsListMessage implements Message, PaginationInfo<TelegramUser> {
    public final static String CLIENTS_LIST_INITIAL_EXIST = "clients.list.initial.exist";
    public final static String CLIENTS_LIST_INITIAL_NOT_EXIST = "clients.list.initial.notExist";
    public final static String CLIENTS_LIST_HEADER = "clients.list.header";

    private LocalMessageSource lms;
    private DetailsCreator detailsCreator;

    @Override
    public String getMessage(UserData userData) {
        return lms.getLocaleMessage(CLIENTS_LIST_HEADER, userData.getUsername()) + detailsCreator.createAllDetails(userData);
    }

    public String initialMessage(UserData userData) {
        Page<TelegramUser> page = userData.getPage();
        return (page != null && !page.isEmpty()) ?
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_EXIST, userData.getUsername()) +
                        paginationInfoLocalMessage(page, lms) :
                lms.getLocaleMessage(CLIENTS_LIST_INITIAL_NOT_EXIST, userData.getUsername());
    }
}
