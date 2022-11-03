package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.DateValidator;
import com.godeltech.gbf.service.validator.exceptions.EmptyButtonCalendarException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.godeltech.gbf.model.State.DATE;

@Service
@AllArgsConstructor
public class DateHandlerType implements HandlerType {

    private DateValidator dateValidator;

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
                dateValidator.checkPastInDate(parsedDate, sessionData.getCallbackQueryId());
                if (!isDateExist(parsedDate, sessionData)) handleChosenDate(parsedDate, sessionData);
            }
            case NEXT, PREVIOUS -> sessionData.getCallbackHistory().remove(1);
            case IGNORE -> throw new EmptyButtonCalendarException(split[1], sessionData.getCallbackQueryId());
            case CONFIRM_DATE -> {
                sessionData.setStartDate(LocalDate.from(sessionData.getTempStartDate()));
                sessionData.setEndDate(LocalDate.from(sessionData.getTempEndDate()));
                sessionData.setTempStartDate(null);
                sessionData.setTempEndDate(null);
            }
            case CLEAR_DATES -> {
                sessionData.setTempStartDate(null);
                sessionData.setTempEndDate(null);
            }
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
