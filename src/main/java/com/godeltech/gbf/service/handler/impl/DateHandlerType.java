package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.DateValidator;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.godeltech.gbf.handling.BotExceptionHandler.ALERT_CALENDAR_DAY_MONTH_CODE;
import static com.godeltech.gbf.handling.BotExceptionHandler.ALERT_CALENDAR_EMPTY_DAY_CODE;
import static com.godeltech.gbf.model.State.DATE;

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
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        CalendarBotButton clickedButton = CalendarBotButton.valueOf(split[0]);
        switch (clickedButton) {
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                dateValidator.checkPastInDate(parsedDate, sessionData.getCallbackQueryId(), sessionData.getLanguage());
                if (!isDateExist(parsedDate, sessionData)) handleChosenDate(parsedDate, sessionData);
            }
            case NEXT, PREVIOUS -> sessionData.getCallbackHistory().remove(1);
            case IGNORE -> {
                String callbackContent = split[1];
                String neededAlertCode = callbackContent.equals("emptyDay") ?
                        ALERT_CALENDAR_EMPTY_DAY_CODE :
                        ALERT_CALENDAR_DAY_MONTH_CODE;
                LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
                throw new GbfAlertException(sessionData.getCallbackQueryId(), lms.getLocaleMessage(neededAlertCode));
            }
            case CONFIRM_DATE -> {
                LocalDate tempStartDate = sessionData.getTempStartDate();
                LocalDate tempEndDate = sessionData.getTempEndDate();
                sessionData.setStartDate(
                        Objects.isNull(tempStartDate) ?
                                null :
                                LocalDate.from(tempStartDate));
                sessionData.setEndDate(
                        Objects.isNull(tempEndDate) ?
                                null :
                                LocalDate.from(tempEndDate));
                sessionData.clearTemp();
            }
            case CLEAR_DATES -> sessionData.clearTemp();
        }
        return clickedButton.getNextState();
    }

    private boolean isDateExist(LocalDate chosenDate, SessionData sessionData) {
        LocalDate startDate = sessionData.getTempStartDate();
        LocalDate endDate = sessionData.getTempEndDate();
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) return false;
        if (Objects.equals(startDate, chosenDate)) {
            sessionData.setTempStartDate(endDate);
            return true;
        }
        if (Objects.equals(endDate, chosenDate)) {
            sessionData.setTempEndDate(startDate);
            return true;
        }
        return false;
    }

    private void handleChosenDate(LocalDate chosenDate, SessionData sessionData) {
        LocalDate startDate = sessionData.getTempStartDate();
        LocalDate endDate = sessionData.getTempEndDate();
        if (startDate == null && endDate == null) {
            sessionData.setTempStartDate(chosenDate);
            sessionData.setTempEndDate(chosenDate);
            return;
        }
        if (chosenDate.isBefore(startDate)) {
            sessionData.setTempStartDate(chosenDate);
            return;
        }
        if (chosenDate.isAfter(endDate) || chosenDate.isAfter(startDate)) {
            sessionData.setTempEndDate(chosenDate);
        }
    }
}
