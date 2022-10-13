package com.godeltech.gbf.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public final static String DAY_PATTERN = "dd";
    public final static String FULL_MONTH_PATTERN = "MMMM";
    public final static String SHORT_MONTH_PATTERN = "MM";
    public final static String FULL_YEAR_PATTERN = "yyyy";
    public final static String SHORT_YEAR_PATTERN = "yy";
    public final static String SPACE = " ";
    public final static String POINT = ".";

    public static String fullFormatDate(LocalDate date) {
        String datePattern = DAY_PATTERN + SPACE + FULL_MONTH_PATTERN + SPACE + FULL_YEAR_PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return date.format(dateTimeFormatter);
    }

    public static String shortFormatDate(LocalDate date) {
        String datePattern = DAY_PATTERN + POINT + SHORT_MONTH_PATTERN + POINT + SHORT_YEAR_PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return date.format(dateTimeFormatter);
    }

    public static String fullFormatDate(LocalDate date, Locale locale) {
        String datePattern = DAY_PATTERN + SPACE + FULL_MONTH_PATTERN + SPACE + FULL_YEAR_PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern).withLocale(locale);
        return date.format(dateTimeFormatter);
    }

    public static String formatMonth(LocalDate date, Locale locale) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FULL_MONTH_PATTERN).withLocale(locale);
        return date.format(dateTimeFormatter);
    }

    public static String formatYear(LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FULL_YEAR_PATTERN);
        return date.format(dateTimeFormatter);
    }

}
