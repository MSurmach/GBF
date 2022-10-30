package com.godeltech.gbf.controller;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.service.alert.Alert;
import com.godeltech.gbf.service.validator.exceptions.EmptyButtonCalendarException;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class BotExceptionHandler {
    public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    private Alert alert;
    private LocalMessageSource lms;


    @ExceptionHandler(GbfException.class)
    public void handleCountryNotFoundException(GbfException exception) {
        String callbackQueryId = exception.getCallbackQueryId();
        String alertMessage = exception.getAlertMessage();
        alert.showAlert(callbackQueryId, alertMessage);
    }

    @ExceptionHandler(EmptyButtonCalendarException.class)
    public void handleEmptyButton(EmptyButtonCalendarException emptyButtonCalendarException) {
        String callback = emptyButtonCalendarException.getButtonCallback();
        String neededAlertCode = callback.equals("emptyDay") ?
                ALERT_CALENDAR_EMPTY_DAY_CODE :
                ALERT_CALENDAR_DAY_MONTH_CODE;
        String callbackQueryId = emptyButtonCalendarException.getCallbackQueryId();
        String alertMessage = lms.getLocaleMessage(neededAlertCode);
        alert.showAlert(callbackQueryId, alertMessage);
    }

    @ExceptionHandler(WrongInputException.class)
    public void handleWrongInput(WrongInputException exception) {

    }

    @ExceptionHandler(Exception.class)
    public void handleAll(Exception exception) {

    }
}
