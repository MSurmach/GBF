package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.utils.DateUtils;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.gui.utils.MessageUtils.datesDetails;

@Service
@AllArgsConstructor
public class DateMessageType implements MessageType {

    public final static String DATE_QUESTION_CODE = "date.question";
    public final static String DATE_TODAY_CODE = "date.today";
    public final static String DATE_INFO_SELECTED_CODE = "date.info.selected";
    public final static String DATE_INFO_NOT_SELECTED_CODE = "date.info.notSelected";

    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DATE;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        String nowDate = DateUtils.shortFormatDate(LocalDate.now());
        String nowDateInfo = lms.getLocaleMessage(DATE_TODAY_CODE, nowDate);
        return nowDateInfo +
                datesDetails(sessionData.getTempStartDate(), sessionData.getTempEndDate(), lms) +
                lms.getLocaleMessage(DATE_QUESTION_CODE);
    }
}
