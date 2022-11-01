package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CalendarBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
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
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] split = callback.split(":");
        CalendarBotButton clickedButton = CalendarBotButton.valueOf(split[0]);
        switch (clickedButton) {
            case SELECT_DAY -> {
                LocalDate parsedDate = LocalDate.parse(split[1]);
                dateValidator.checkPastInDate(parsedDate, userData.getCallbackQueryId());
                if (!isDateExist(parsedDate, userData)) handleChosenDate(parsedDate, userData);
            }
            case NEXT, PREVIOUS -> userData.getCallbackHistory().remove(1);
            case IGNORE -> throw new EmptyButtonCalendarException(split[1], userData.getCallbackQueryId());
            case CONFIRM_DATE -> {
                userData.setStartDate(LocalDate.from(userData.getTempStartDate()));
                userData.setEndDate(LocalDate.from(userData.getTempEndDate()));
                userData.setTempStartDate(null);
                userData.setTempEndDate(null);
            }
        }
        return clickedButton.getNextState();
    }

    private boolean isDateExist(LocalDate chosenDate, UserData userData) {
        LocalDate startDate = userData.getTempStartDate();
        LocalDate endDate = userData.getTempEndDate();
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) return false;
        if (Objects.equals(startDate, chosenDate)) {
            userData.setTempStartDate(endDate);
            return true;
        }
        if (Objects.equals(endDate, chosenDate)) {
            userData.setTempEndDate(startDate);
            return true;
        }
        return false;
    }

    private void handleChosenDate(LocalDate chosenDate, UserData userData) {
        LocalDate startDate = userData.getTempStartDate();
        LocalDate endDate = userData.getTempEndDate();
        if (startDate == null && endDate == null) {
            userData.setTempStartDate(chosenDate);
            userData.setTempEndDate(chosenDate);
            return;
        }
        if (chosenDate.isBefore(startDate)) {
            userData.setTempStartDate(chosenDate);
            return;
        }
        if (chosenDate.isAfter(endDate) || chosenDate.isAfter(startDate)) {
            userData.setTempEndDate(chosenDate);
        }
    }
}
