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
public class RegistrationsMessage implements Message {
    private LocalMessageSource localMessageSource;
    private SummaryDataMessage summaryDataText;

    public final static String REGISTRATIONS_EXIST_CODE = "registrations.exist";
    public final static String REGISTRATIONS_NOT_EXIST_CODE = "registrations.notExist";
    public final static String REGISTRATIONS_PAGINATION_INFO_CODE= "registrations.pagination.info";
    public final static String REGISTRATION_DATA_RECORD_ID = "registration.data.record_id";


    @Override
    public String getMessage(UserData userData) {
        String recordIdHeader = localMessageSource.getLocaleMessage(REGISTRATION_DATA_RECORD_ID, String.valueOf(userData.getRecordId()));
        return recordIdHeader + summaryDataText.getMessage(userData);
    }

    public String initialMessage(UserData userData) {
        Page<UserRecord> records = userData.getRecordsPage();
        String username = userData.getUsername();
        return (records != null && !records.isEmpty()) ?
                localMessageSource.getLocaleMessage(REGISTRATIONS_EXIST_CODE, username) +
                        paginationInfoMessage(userData) :
                localMessageSource.getLocaleMessage(REGISTRATIONS_NOT_EXIST_CODE, username);
    }

    private String paginationInfoMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(REGISTRATIONS_PAGINATION_INFO_CODE,
                String.valueOf(userData.getRecordsPage().getTotalElements()));
    }
}
