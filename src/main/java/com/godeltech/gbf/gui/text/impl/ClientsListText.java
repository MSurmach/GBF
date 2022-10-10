package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientsListText implements Text {
    public final static String CLIENTS_LIST_INITIAL_EXIST = "clients.list.initial.exist";
    public final static String CLIENTS_LIST_INITIAL_NOT_EXIST = "clients.list.initial.notExist";
    public final static String CLIENTS_LIST_HEADER = "clients.list.header";
    public final static String CLIENTS_LIST_PAGINATION_INFO = "clients.list.pagination.info";


    private LocalMessageSource localMessageSource;
    private SummaryDataText summaryDataText;

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(CLIENTS_LIST_HEADER, userData.getUsername()) +
                summaryDataText.getText(userData);
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
