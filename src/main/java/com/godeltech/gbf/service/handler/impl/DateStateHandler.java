package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.DateAfterDateException;
import com.godeltech.gbf.exception.DateInPastException;
import com.godeltech.gbf.exception.EmptyButtonCalendarException;
import com.godeltech.gbf.management.button.CalendarBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.*;

@Service
public class DateStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        var clickedButton = CalendarBotButton.valueOf(split[0]);
        State currentState = userData.getStateHistory().peek();
        return switch (clickedButton) {
            case CHANGE_MONTH -> currentState == DATE_FROM ? MONTH_FROM : MONTH_TO;
            case CHANGE_YEAR -> currentState == DATE_FROM ? YEAR_FROM : YEAR_TO;
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                catchDate(userData, parsedDate);
                yield selectNextState(userData);
            }
            case IGNORE -> throw new EmptyButtonCalendarException(split[1], userData.getCallbackQueryId());
            default -> currentState;
        };
    }

    private void catchDate(UserData userData, LocalDate date) {
        String callbackQueryId = userData.getCallbackQueryId();
        checkPastInDate(date, callbackQueryId);
        State currentState = userData.getStateHistory().peek();
        if (currentState == DATE_FROM) userData.setDateFrom(date);
        else {
            checkDateToAfterDateFrom(userData.getDateFrom(), date, callbackQueryId);
            userData.setDateTo(date);
        }
    }

    private State selectNextState(UserData userData) {
        Role role = userData.getRole();
        State currentState = userData.getStateHistory().peek();
        return switch (role) {
            case CUSTOMER, COURIER -> currentState == DATE_FROM ? COUNTRY_TO : CARGO_MENU;
            case REGISTRATIONS_VIEWER -> REGISTRATION_EDITOR;
        };
    }

    private void checkPastInDate(LocalDate date, String callbackQueryId) {
        LocalDate nowDate = LocalDate.now();
        if (date.isBefore(nowDate)) throw new DateInPastException(date, nowDate, callbackQueryId);
    }

    private void checkDateToAfterDateFrom(LocalDate dateFrom, LocalDate dateTo, String callbackQueryId) {
        if (dateFrom != null && dateTo.isBefore(dateFrom))
            throw new DateAfterDateException(dateFrom, dateTo, callbackQueryId);
    }
}
