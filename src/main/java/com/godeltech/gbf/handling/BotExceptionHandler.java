package com.godeltech.gbf.handling;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.exception.DeleteMessageException;
import com.godeltech.gbf.exception.MembershipException;
import com.godeltech.gbf.exception.MessageFromGroupException;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.service.alert.ExceptionResponseService;
import com.godeltech.gbf.service.validator.exceptions.EmptyButtonCalendarException;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.objects.User;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class BotExceptionHandler {
    public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    public final static String TEXT_MESSAGE_OF_MEMBERSHIP = "membership.answer";
    private ExceptionResponseService exceptionResponseService;
    private LocalMessageSource lms;


    @ExceptionHandler(GbfException.class)
    public void handleCountryNotFoundException(GbfException exception) {
        String callbackQueryId = exception.getCallbackQueryId();
        String alertMessage = exception.getAlertMessage();
        exceptionResponseService.showAlert(callbackQueryId, alertMessage);
    }

    @ExceptionHandler(EmptyButtonCalendarException.class)
    public void handleEmptyButton(EmptyButtonCalendarException emptyButtonCalendarException) {
        String callback = emptyButtonCalendarException.getButtonCallback();
        String neededAlertCode = callback.equals("emptyDay") ?
                ALERT_CALENDAR_EMPTY_DAY_CODE :
                ALERT_CALENDAR_DAY_MONTH_CODE;
        String callbackQueryId = emptyButtonCalendarException.getCallbackQueryId();
        String alertMessage = lms.getLocaleMessage(neededAlertCode);
        exceptionResponseService.showAlert(callbackQueryId, alertMessage);
    }

    @ExceptionHandler(WrongInputException.class)
    public void handleWrongInput(WrongInputException exception) {

    }

    @ExceptionHandler(MembershipException.class)
    public void handleMembershipException(MembershipException exception){
        User user = exception.getBotMessage().getFrom();
        log.error("User isn't a member of chmoki group with username : {} and id : {}",
                user.getUserName(),user.getId());
        exceptionResponseService.makeSendMessage(exception.getBotMessage(),
                lms.getLocaleMessage(TEXT_MESSAGE_OF_MEMBERSHIP));
    }

    @ExceptionHandler(MessageFromGroupException.class)
    public void handleMessageFromGroupException(MessageFromGroupException exception){
//        Do nothing !!
    }

    @ExceptionHandler(DeleteMessageException.class)
    public void handleDeleteMessageException(DeleteMessageException exception){
        log.error(exception.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public void handleAll(Exception exception) {
//
//    }
}
