package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationsMainText implements Text {
    private LocalMessageSource localMessageSource;

    public final static String REGISTRATIONS_MAIN_EXIST_CODE = "registrations.main.exist";
    public final static String REGISTRATIONS_MAIN_NOT_EXIST_CODE = "registrations.main.not_exist";

    @Override
    public String getText(UserData userData) {
        List<UserRecord> records = userData.getRecords();
        if (records != null && !records.isEmpty()) {
            return localMessageSource.getLocaleMessage(REGISTRATIONS_MAIN_EXIST_CODE, userData.getUsername());
        } else return localMessageSource.getLocaleMessage(REGISTRATIONS_MAIN_NOT_EXIST_CODE, userData.getUsername());
    }
}
