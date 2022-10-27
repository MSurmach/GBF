package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.validator.DateValidator;
import com.godeltech.gbf.service.validator.exceptions.EmptyButtonCalendarException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class DateHandlerType implements HandlerType {

    private DateValidator dateValidator;

    @Override
    public State getState() {
        return DATE;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        CalendarBotButton clickedButton = CalendarBotButton.valueOf(split[0]);
        State currentState = userData.getStateHistory().peek();
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        return switch (clickedButton) {
            case CHANGE_MONTH -> MONTH;
            case CHANGE_YEAR -> YEAR;
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                dateValidator.checkPastInDate(parsedDate, userData.getCallbackQueryId());
                handleChosenDate(parsedDate, tempRoutePoint);
                yield currentState;
            }
            case NEXT, PREVIOUS -> {
                userData.getCallbackHistory().remove(1);
                yield userData.getStateHistory().pop();
            }
            case IGNORE -> throw new EmptyButtonCalendarException(split[1], userData.getCallbackQueryId());
            case CONFIRM_DATE -> ROUTE_POINT_FORM;
            case CLEAR_DATE_SELECT -> {
//                tempRoutePoint.setStartDate(null);
//                tempRoutePoint.setEndDate(null);
                yield currentState;
            }
            default -> currentState;
        };
    }

    private void handleChosenDate(LocalDate chosenDate, RoutePoint routePoint) {
//        LocalDate startDate = routePoint.getStartDate();
//        LocalDate endDate = routePoint.getEndDate();
//        if (startDate == null && endDate == null) {
//            routePoint.setStartDate(chosenDate);
//            routePoint.setEndDate(chosenDate);
//            return;
//        }
//        if (chosenDate.isBefore(startDate)) {
//            routePoint.setStartDate(chosenDate);
//            return;
//        }
//        if (chosenDate.isAfter(endDate) || chosenDate.isAfter(startDate)) {
//            routePoint.setEndDate(chosenDate);
//            return;
//        }
    }
}
