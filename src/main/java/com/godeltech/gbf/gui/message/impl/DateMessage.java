package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateMessage implements Message {

    public final static String DATE_QUESTION_CODE = "date.question";
    public final static String DATE_TODAY_CODE = "date.today";
    public final static String DATE_INFO_SELECTED_CODE = "date.info.selected";
    public final static String DATE_INFO_NOT_SELECTED_CODE = "date.info.notSelected";

    private final LocalMessageSource lms;

    @Override
    public String getMessage(UserData userData) {
        String nowDate = DateUtils.shortFormatDate(LocalDate.now());
        String nowDateInfo = lms.getLocaleMessage(DATE_TODAY_CODE, nowDate);
        return nowDateInfo +
                buildInfoAboutDates(userData.getTempRoutePoint()) +
                lms.getLocaleMessage(DATE_QUESTION_CODE);
    }

    private String buildInfoAboutDates(RoutePoint routePoint) {
        LocalDate startDate = routePoint.getStartDate();
        LocalDate endDate = routePoint.getEndDate();
        if (startDate == null && endDate == null) return lms.getLocaleMessage(DATE_INFO_NOT_SELECTED_CODE);
        return startDate.equals(endDate) ?
                lms.getLocaleMessage(DATE_INFO_SELECTED_CODE, DateUtils.shortFormatDate(startDate)) :
                lms.getLocaleMessage(DATE_INFO_SELECTED_CODE, DateUtils.dateAsRange(startDate, endDate));
    }
}
