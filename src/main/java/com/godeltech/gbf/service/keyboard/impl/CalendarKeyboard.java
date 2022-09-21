package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.CalendarCommand.*;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class CalendarKeyboard extends LocaleKeyboard {
    private Keyboard controlKeyboard;

    public CalendarKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String givenDate = callback.split(":")[1];
        LocalDate date = LocalDate.parse(givenDate);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        addMonthYear(date, keyboard);
        addWeekDayRow(keyboard);
        addDayRows(date, keyboard);
        var calendarKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(calendarKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }

    private void addDayRows(LocalDate localDate, List<List<InlineKeyboardButton>> keyboard) {
        LocalDate firstDayDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
        int shift = firstDayDate.getDayOfWeek().getValue() - 1;
        int columnCount = 7;
        int lengthOfMonth = localDate.lengthOfMonth();
        int numOfRows = (int) Math.ceil(((double) shift + lengthOfMonth) / columnCount);
        for (int index = 0; index < numOfRows; index++) {
            keyboard.add(buildDayRow(firstDayDate, shift, columnCount));
            firstDayDate = firstDayDate.plusDays(columnCount - shift);
            shift = 0;
        }
    }

    private void addMonthYear(LocalDate date, List<List<InlineKeyboardButton>> keyboard) {
        var prevMonthButton = createButton(
                PREV.getDescription(),
                PREV.name() + ":" + date.minusMonths(1));
        var nextMonthButton = createButton(
                NEXT.getDescription(),
                NEXT.name() + ":" + date.plusMonths(1));
        String monthYearPattern = "LLLL yyyy";
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(monthYearPattern).withLocale(localeMessageSource.getLocale());
        String header = date.format(monthYearFormatter).substring(0, 1).toUpperCase() + date.format(monthYearFormatter).substring(1);
        var monthYearHeader = createButton(
                header,
                MONTH.name() + ":" + date);
        keyboard.add(List.of(prevMonthButton, monthYearHeader, nextMonthButton));
    }

    private void addWeekDayRow(List<List<InlineKeyboardButton>> keyboard) {
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, localeMessageSource.getLocale())).
                forEach(day -> weekDayRow.add(createButton(day)));
        keyboard.add(weekDayRow);
    }

    private List<InlineKeyboardButton> buildDayRow(LocalDate date, int shift, int columnCount) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        for (int index = 0; index < shift; index++) {
            row.add(createButton());
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                row.add(createButton(Integer.toString(day++), DAY.name() + ":" + callbackDate));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(createButton());
            }
        }
        return row;
    }
}
