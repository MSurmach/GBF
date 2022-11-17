package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.gui.utils.DateUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
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
public class DateTextMessageType implements TextMessageType {

    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.DATE;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create date message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(), sessionData.getUsername());
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        String nowDate = DateUtils.shortFormatDate(LocalDate.now());
        String nowDateInfo = lms.getLocaleMessage(DATE_TODAY_CODE, nowDate);
        return nowDateInfo +
                datesDetails(sessionData.getTempStartDate(), sessionData.getTempEndDate(), lms) +
                lms.getLocaleMessage(DATE_QUESTION_CODE);
    }
}