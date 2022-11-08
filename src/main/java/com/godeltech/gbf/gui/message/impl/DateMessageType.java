package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.gui.utils.DateUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.gui.utils.ConstantUtil.DATE_QUESTION_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.DATE_TODAY_CODE;
import static com.godeltech.gbf.gui.utils.MessageUtils.datesDetails;

@Service
@AllArgsConstructor
@Slf4j
public class DateMessageType implements MessageType {

    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DATE;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create date message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        String nowDate = DateUtils.shortFormatDate(LocalDate.now());
        String nowDateInfo = lms.getLocaleMessage(DATE_TODAY_CODE, nowDate);
        return nowDateInfo +
                datesDetails(sessionData.getTempStartDate(), sessionData.getTempEndDate(), lms) +
                lms.getLocaleMessage(DATE_QUESTION_CODE);
    }
}
