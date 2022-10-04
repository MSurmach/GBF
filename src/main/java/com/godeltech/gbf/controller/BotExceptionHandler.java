package com.godeltech.gbf.controller;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.exception.*;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.answer.impl.DateAnswer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@AllArgsConstructor
public class BotExceptionHandler {
    public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    public final static String ALERT_CALENDAR_DATE_IN_PAST_CODE = "alert.calendar.dateInPast";
    public final static String ALERT_CALENDAR_DATE_AFTER_DATE_CODE = "alert.calendar.dateAfterDate";
    public final static String ALERT_CARGO_MENU_NOTHING_SELECTED = "alert.cargoMenu.nothingSelected";
    private GbfBot gbfBot;
    private LocalMessageSource localMessageSource;

    private DateAnswer dateAnswer;

    @ExceptionHandler(EmptyButtonCalendarException.class)
    public void handleEmptyButton(EmptyButtonCalendarException emptyButtonCalendarException) {
        String callback = emptyButtonCalendarException.getButtonCallback();
        String neededAlertCode = callback.equals("emptyDay") ?
                ALERT_CALENDAR_EMPTY_DAY_CODE :
                ALERT_CALENDAR_DAY_MONTH_CODE;
        String callbackQueryId = emptyButtonCalendarException.getCallbackQueryId();
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(localMessageSource.getLocaleMessage(neededAlertCode)).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(DateInPastException.class)
    public void handleDateInPast(DateInPastException dateInPastException) {
        LocalDate nowDate = dateInPastException.getNowDate();
        LocalDate invalidDate = dateInPastException.getInvalidDate();
        DateTimeFormatter dateFormatter = dateAnswer.getDateFormatter();
        String callbackQueryId = dateInPastException.getCallbackQueryId();
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(localMessageSource.getLocaleMessage(
                        ALERT_CALENDAR_DATE_IN_PAST_CODE,
                        nowDate.format(dateFormatter),
                        invalidDate.format(dateFormatter))).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(DateAfterDateException.class)
    public void handleDateAfterDate(DateAfterDateException exception) {
        LocalDate dateTo = exception.getDateTo();
        LocalDate dateFrom = exception.getDateFrom();
        String callbackQueryId = exception.getCallbackQueryId();
        DateTimeFormatter dateFormatter = dateAnswer.getDateFormatter();
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(localMessageSource.getLocaleMessage(
                        ALERT_CALENDAR_DATE_AFTER_DATE_CODE,
                        dateFrom.format(dateFormatter),
                        dateTo.format(dateFormatter))).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(ConfirmationException.class)
    public void handleConfirmationException(ConfirmationException exception) {
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(exception.getCallbackQueryId()).
                showAlert(true).
                text(localMessageSource.getLocaleMessage(ALERT_CARGO_MENU_NOTHING_SELECTED)).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(WrongInputException.class)
    public void handleWrongInput(WrongInputException exception) {

    }

    private void showAlert(BotApiMethod<?> method) {
        try {
            gbfBot.execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}