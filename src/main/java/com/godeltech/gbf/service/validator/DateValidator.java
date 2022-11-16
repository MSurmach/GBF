package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.utils.DateUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateValidator {

    private final LocalMessageSourceFactory localMessageSourceFactory;
    public final static String ALERT_DATE_PAST_CODE = "alert.date.past";

    public void checkPastInDate(LocalDate date, String callbackQueryId, String language) {
        LocalMessageSource lms = localMessageSourceFactory.get(language);
        LocalDate nowDate = LocalDate.now();
        if (date.isBefore(nowDate)) {
            String alertMessage = lms.getLocaleMessage(
                    ALERT_DATE_PAST_CODE,
                    DateUtils.shortFormatDate(nowDate),
                    DateUtils.shortFormatDate(date));
            throw new GbfAlertException(callbackQueryId, alertMessage);
        }
    }
}
