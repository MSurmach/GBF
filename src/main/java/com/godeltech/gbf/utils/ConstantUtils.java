package com.godeltech.gbf.utils;

public class ConstantUtils {
    public static class ExceptionMessages{
        public static final String MEMBERSHIP_PATTERN_EXCEPTION ="User with id = %s, and username = %s isn't a member of chmoki group" ;
        public static final String CANNOT_DELETE_MESSAGE_EXCEPTION_PATTERN = "Message with id : %s and chat id : %s can't be deleted or corrected";
        public final static String NO_FOUND_PATTERN = "%s wasn't found by %s=%s";
    }
        public final static String ALERT_CALENDAR_EMPTY_DAY_CODE = "alert.calendar.emptyDay";
    public final static String ALERT_CALENDAR_DAY_MONTH_CODE = "alert.calendar.dayOfMonth";
    public final static String TEXT_MESSAGE_OF_MEMBERSHIP = "membership.answer";
}
