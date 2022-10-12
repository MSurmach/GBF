package com.godeltech.gbf.controller;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.exception.*;
import com.godeltech.gbf.gui.message.impl.DateMessage;
import com.godeltech.gbf.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@ControllerAdvice
@AllArgsConstructor
public class BotExceptionHandler {
    public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    public final static String ALERT_CALENDAR_DATE_IN_PAST_CODE = "alert.calendar.dateInPast";
    public final static String ALERT_CALENDAR_DATE_AFTER_DATE_CODE = "alert.calendar.dateAfterDate";
    public final static String ALERT_CARGO_MENU_NOTHING_SELECTED = "alert.cargoMenu.nothingSelected";
    private GbfBot gbfBot;
    private LocalMessageSource lms;

    private DateMessage dateAnswer;

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
                text(lms.getLocaleMessage(neededAlertCode)).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(DateInPastException.class)
    public void handleDateInPast(DateInPastException dateInPastException) {
        LocalDate nowDate = dateInPastException.getNowDate();
        LocalDate invalidDate = dateInPastException.getInvalidDate();
        String callbackQueryId = dateInPastException.getCallbackQueryId();
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(lms.getLocaleMessage(
                        ALERT_CALENDAR_DATE_IN_PAST_CODE,
                        DateUtils.formatDate(nowDate, lms.getLocale()),
                        DateUtils.formatDate(invalidDate, lms.getLocale()))).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(DateAfterDateException.class)
    public void handleDateAfterDate(DateAfterDateException exception) {
        LocalDate dateTo = exception.getDateTo();
        LocalDate dateFrom = exception.getDateFrom();
        String callbackQueryId = exception.getCallbackQueryId();
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(lms.getLocaleMessage(
                        ALERT_CALENDAR_DATE_AFTER_DATE_CODE,
                        DateUtils.formatDate(dateFrom, lms.getLocale()),
                        DateUtils.formatDate(dateTo, lms.getLocale()))).
                cacheTime(60).
                build();
        showAlert(answerCallbackQuery);
    }

    @ExceptionHandler(ConfirmationException.class)
    public void handleConfirmationException(ConfirmationException exception) {
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder().
                callbackQueryId(exception.getCallbackQueryId()).
                showAlert(true).
                text(lms.getLocaleMessage(ALERT_CARGO_MENU_NOTHING_SELECTED)).
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
