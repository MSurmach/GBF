package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationRecordText implements Text {

    private SummaryDataText summaryDataText;
    private LocalMessageSource localMessageSource;
    public final static String REGISTRATIONS_MAIN_DATA_RECORD_ID_CODE = "registration.data.record_id";

    @Override
    public String getText(UserData userData) {
        String recordIdHeader = localMessageSource.getLocaleMessage(REGISTRATIONS_MAIN_DATA_RECORD_ID_CODE, String.valueOf(userData.getRecordId()));
        return recordIdHeader + summaryDataText.getText(userData);
    }
}
