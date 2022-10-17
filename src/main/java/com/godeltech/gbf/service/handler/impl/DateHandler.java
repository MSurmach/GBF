package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.validator.DateValidator;
import com.godeltech.gbf.service.validator.exceptions.EmptyButtonCalendarException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class DateHandler implements Handler {

    private DateValidator dateValidator;

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
                dateValidator.checkPastInDate(parsedDate, userData.getCallbackQueryId());
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
}
