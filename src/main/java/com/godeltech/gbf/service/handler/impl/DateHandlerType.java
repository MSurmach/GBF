package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.DateValidator;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;


import static com.godeltech.gbf.model.State.DATE;
import static com.godeltech.gbf.utils.ConstantUtils.ALERT_CALENDAR_DAY_MONTH_CODE;
import static com.godeltech.gbf.utils.ConstantUtils.ALERT_CALENDAR_EMPTY_DAY_CODE;

@Service
@AllArgsConstructor
public class DateHandlerType implements HandlerType {

    private final DateValidator dateValidator;
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return DATE;
    }

    @Override
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        String[] split = callback.split(":");
        CalendarBotButton clickedButton = CalendarBotButton.valueOf(split[0]);
        switch (clickedButton) {
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                dateValidator.checkPastInDate(parsedDate, session.getCallbackQueryId(), session.getTelegramUser().getLanguage());
                if (!isDateExist(parsedDate, session)) handleChosenDate(parsedDate, session);
            }
            case NEXT, PREVIOUS -> session.getCallbackHistory().remove(1);
            case IGNORE -> {
                String callbackContent = split[1];
                String neededAlertCode = callbackContent.equals("emptyDay") ?
                        ALERT_CALENDAR_EMPTY_DAY_CODE :
                        ALERT_CALENDAR_DAY_MONTH_CODE;
                LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
                throw new GbfAlertException(session.getCallbackQueryId(), lms.getLocaleMessage(neededAlertCode));
            }
            case CONFIRM_DATE -> {
                LocalDate tempStartDate = session.getTempStartDate();
                LocalDate tempEndDate = session.getTempEndDate();
                session.setStartDate(
                        Objects.isNull(tempStartDate) ?
                                null :
                                LocalDate.from(tempStartDate));
                session.setEndDate(
                        Objects.isNull(tempEndDate) ?
                                null :
                                LocalDate.from(tempEndDate));
                session.clearTemp();
            }
            case CLEAR_DATES -> session.clearTemp();
        }
        return clickedButton.getNextState();
    }

    private boolean isDateExist(LocalDate chosenDate, Session session) {
        LocalDate startDate = session.getTempStartDate();
        LocalDate endDate = session.getTempEndDate();
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) return false;
        if (Objects.equals(startDate, chosenDate)) {
            session.setTempStartDate(endDate);
            return true;
        }
        if (Objects.equals(endDate, chosenDate)) {
            session.setTempEndDate(startDate);
            return true;
        }
        return false;
    }

    private void handleChosenDate(LocalDate chosenDate, Session session) {
        LocalDate startDate = session.getTempStartDate();
        LocalDate endDate = session.getTempEndDate();
        if (startDate == null && endDate == null) {
            session.setTempStartDate(chosenDate);
            session.setTempEndDate(chosenDate);
            return;
        }
        if (chosenDate.isBefore(startDate)) {
            session.setTempStartDate(chosenDate);
            return;
        }
        if (chosenDate.isAfter(endDate) || chosenDate.isAfter(startDate)) {
            session.setTempEndDate(chosenDate);
        }
    }
}
