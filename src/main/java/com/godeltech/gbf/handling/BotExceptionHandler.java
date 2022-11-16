package com.godeltech.gbf.handling;

import com.godeltech.gbf.exception.*;
import com.godeltech.gbf.service.alert.ExceptionResponseService;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class BotExceptionHandler {
    public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    public final static String TEXT_MESSAGE_OF_MEMBERSHIP = "membership.answer";
    private ExceptionResponseService exceptionResponseService;


    @ExceptionHandler(GbfAlertException.class)
    public void handleCountryNotFoundException(GbfAlertException exception) {
        log.info(exception.getAlertMessage());
        String callbackQueryId = exception.getCallbackQueryId();
        String alertMessage = exception.getAlertMessage();
        exceptionResponseService.showAlert(callbackQueryId, alertMessage);
    }

//    @ExceptionHandler(EmptyButtonCalendarException.class)
//    public void handleEmptyButton(EmptyButtonCalendarException emptyButtonCalendarException) {
//        log.info(emptyButtonCalendarException.getClass().getName());
//        String callback = emptyButtonCalendarException.getButtonCallback();
//        String neededAlertCode = callback.equals("emptyDay") ?
//                ALERT_CALENDAR_EMPTY_DAY_CODE :
//                ALERT_CALENDAR_DAY_MONTH_CODE;
//        String callbackQueryId = emptyButtonCalendarException.getCallbackQueryId();
//        String alertMessage = lms.getLocaleMessage(neededAlertCode);
//        exceptionResponseService.showAlert(callbackQueryId, alertMessage);
//    }

    @ExceptionHandler(MembershipException.class)
    public void handleMembershipException(MembershipException exception) {
//        User user = exception.getBotMessage().getFrom();
//        log.error("User isn't a member of chmoki group with username : {} and id : {}",
//                user.getUserName(), user.getId());
//        exceptionResponseService.makeSendMessage(exception.getBotMessage(),
//                lms.getLocaleMessage(TEXT_MESSAGE_OF_MEMBERSHIP));
    }

    @ExceptionHandler(MessageFromGroupException.class)
    public void handleMessageFromGroupException(MessageFromGroupException exception) {
//        Do nothing !!
    }

    @ExceptionHandler(DeleteMessageException.class)
    public void handleDeleteMessageException(DeleteMessageException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleAll(RuntimeException exception) {
        log.error("Get runtime exception with message : {} and with stacktrace : {}",
                exception.getMessage(), exception.getStackTrace());
    }

    @ExceptionHandler(NotFoundStateTypeException.class)
    public void handleNotFoundStateTypeException(NotFoundStateTypeException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error(exception.getMessage());
    }
}
