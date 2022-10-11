package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientsListMessage implements Message {
    public final static String CLIENTS_LIST_INITIAL_EXIST = "clients.list.initial.exist";
    public final static String CLIENTS_LIST_INITIAL_NOT_EXIST = "clients.list.initial.notExist";
    public final static String CLIENTS_LIST_HEADER = "clients.list.header";
    public final static String CLIENTS_LIST_PAGINATION_INFO = "clients.list.pagination.info";

    private LocalMessageSource localMessageSource;
    private SummaryDataMessage summaryDataText;

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CLIENTS_LIST_HEADER, userData.getUsername()) +
                summaryDataText.getMessage(userData);
    }

    public String initialMessage(UserData userData) {
        return (userData.getRecordsPage() != null && !userData.getRecordsPage().isEmpty()) ?
                localMessageSource.getLocaleMessage(CLIENTS_LIST_INITIAL_EXIST, userData.getUsername()) +
                        paginationInfoMessage(userData) :
                localMessageSource.getLocaleMessage(CLIENTS_LIST_INITIAL_NOT_EXIST, userData.getUsername());
    }

    public String paginationInfoMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CLIENTS_LIST_PAGINATION_INFO,
                String.valueOf(userData.getRecordsPage().getTotalElements()));
    }
}
