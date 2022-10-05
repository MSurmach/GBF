package com.godeltech.gbf.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public final static String DAY_PATTERN = "d";
    public final static String MONTH_PATTERN = "MMMM";
    public final static String YEAR_PATTERN = "yyyy";
    public final static String SPACE = " ";

    public static String formatDate(LocalDate date) {
        String datePattern = DAY_PATTERN + SPACE + MONTH_PATTERN + SPACE + YEAR_PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return date.format(dateTimeFormatter);
    }

    public static String formatDate(LocalDate date, Locale locale) {
        String datePattern = DAY_PATTERN + SPACE + MONTH_PATTERN + SPACE + YEAR_PATTERN;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern).withLocale(locale);
        return date.format(dateTimeFormatter);
    }
}
