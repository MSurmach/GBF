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
public class FoundCouriersInfoText implements Text {
    private LocalMessageSource localMessageSource;
    public final static String COURIERS_COUNT_CODE = "couriers.count";
    public final static String COURIERS_NOT_FOUND_CODE = "couriers.notfound";
    public final static String COURIERS_FOUND_CODE = "couriers.found";

    @Override
    public String getText(UserData userData) {
        List<UserRecord> records = userData.getRecords();
        int count = records.size();
        String messageCode = count == 0 ? COURIERS_NOT_FOUND_CODE : COURIERS_FOUND_CODE;

        return localMessageSource.getLocaleMessage(COURIERS_COUNT_CODE, String.valueOf(count)) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(messageCode);
    }
}
