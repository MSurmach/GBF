package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequestsMessage implements Message {
    private LocalMessageSource localMessageSource;

    public final static String REQUESTS_EXIST_INITIAL = "requests.exist.initial";
    public final static String REQUESTS_NOT_EXIST_INITIAL = "requests.notExist.initial";
    public final static String REQUESTS_LIST_PAGINATION_INFO = "requests.list.pagination.info";
    public final static String REQUESTS_DATA_RECORD_ID = "request.data.record_id";

    @Override
    public String getMessage(UserData userData) {
        String recordIdHeader = localMessageSource.getLocaleMessage(REQUESTS_DATA_RECORD_ID, String.valueOf(userData.getUserId()));
        return recordIdHeader /*+ summaryDataText.getMessage(userData)*/;
    }

    public String initialMessage(UserData userData) {
        Page<UserRecord> records = userData.getRecordsPage();
        String username = userData.getUsername();
        return (records != null && !records.isEmpty()) ?
                localMessageSource.getLocaleMessage(REQUESTS_EXIST_INITIAL, username) +
                        paginationInfoMessage(userData) :
                localMessageSource.getLocaleMessage(REQUESTS_NOT_EXIST_INITIAL, username);
    }

    private String paginationInfoMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(REQUESTS_LIST_PAGINATION_INFO,
                String.valueOf(userData.getRecordsPage().getTotalElements()));
    }
}
