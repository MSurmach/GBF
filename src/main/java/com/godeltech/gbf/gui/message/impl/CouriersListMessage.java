package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouriersListMessage implements Message {
    public final static String COURIERS_LIST_INITIAL_EXIST = "couriers.list.initial.exist";
    public final static String COURIERS_LIST_INITIAL_NOT_EXIST = "couriers.list.initial.notExist";
    public final static String COURIERS_LIST_HEADER = "couriers.list.header";
    public final static String COURIERS_LIST_PAGINATION_INFO = "couriers.list.pagination.info";
    private LocalMessageSource localMessageSource;
    private SummaryDataMessage summaryDataText;

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(COURIERS_LIST_HEADER, userData.getUsername()) +
                summaryDataText.getMessage(userData);
    }

    public String initialMessage(UserData userData) {
        return (userData.getRecordsPage() != null && !userData.getRecordsPage().isEmpty()) ?
                localMessageSource.getLocaleMessage(COURIERS_LIST_INITIAL_EXIST, userData.getUsername()) +
                        paginationInfoMessage(userData) :
                localMessageSource.getLocaleMessage(COURIERS_LIST_INITIAL_NOT_EXIST, userData.getUsername());
    }

    public String paginationInfoMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(COURIERS_LIST_PAGINATION_INFO,
                String.valueOf(userData.getRecordsPage().getTotalElements()));
    }
}
