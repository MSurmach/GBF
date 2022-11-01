package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import com.godeltech.gbf.gui.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DateValidator {

    private LocalMessageSource lms;

    public final static String ALERT_DATE_PAST_CODE = "alert.date.past";

    public void checkPastInDate(LocalDate date, String callbackQueryId) {
        LocalDate nowDate = LocalDate.now();
        if (date.isBefore(nowDate)) {
            String alertMessage = lms.getLocaleMessage(
                    ALERT_DATE_PAST_CODE,
                    DateUtils.shortFormatDate(nowDate),
                    DateUtils.shortFormatDate(date));
            throw new GbfException(callbackQueryId, alertMessage);
        }
    }

    public void checkDatesInRoutePoint(List<RoutePoint> routePoints) {
        for (int index = 0; index < routePoints.size(); index++) {

        }
    }
}
