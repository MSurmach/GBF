package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.DateAfterDateException;
import com.godeltech.gbf.exception.DateInPastException;
import com.godeltech.gbf.exception.EmptyButtonCalendarException;
import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.*;

@Service
public class DateHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        CalendarBotButton clickedButton = CalendarBotButton.valueOf(split[0]);
        State currentState = userData.getStateHistory().peek();
        return switch (clickedButton) {
            case CHANGE_MONTH -> MONTH;
            case CHANGE_YEAR -> YEAR;
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                userData.getTempRoutePoint().setVisitDate(parsedDate);
                yield ROUTE_POINT_FORM;
            }
            case NEXT, PREVIOUS -> {
                userData.getCallbackHistory().remove(1);
                yield userData.getStateHistory().pop();
            }
            case IGNORE -> throw new EmptyButtonCalendarException(split[1], userData.getCallbackQueryId());
            default -> currentState;
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
