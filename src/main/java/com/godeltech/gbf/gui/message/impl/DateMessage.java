package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateMessage implements Message {

    public final static String DATE_QUESTION_CODE = "date.question";
    public final static String DATE_TODAY_CODE = "date.today";
    private final LocalMessageSource lms;

    @Override
    public String getMessage(UserData userData) {
        String nowDate = DateUtils.formatDate(LocalDate.now(), lms.getLocale());
        String nowDateInfo = lms.getLocaleMessage(DATE_TODAY_CODE, nowDate);
        return nowDateInfo + lms.getLocaleMessage(DATE_QUESTION_CODE);
    }
}
