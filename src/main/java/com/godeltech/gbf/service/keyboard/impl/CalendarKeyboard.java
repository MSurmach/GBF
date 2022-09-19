package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
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

import static com.godeltech.gbf.model.CalendarButtons.NEXT_MONTH;
import static com.godeltech.gbf.model.CalendarButtons.PREV_MONTH;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class CalendarKeyboard extends LocaleKeyboard implements KeybordAddData {
    private Keyboard controlKeyboard;

    public CalendarKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        LocalDate localDate = LocalDate.now();
        var monthYearHeader = createMonthYearRow(localDate);
        var weekDayRow = createWeekDayRow();
        var daysMarkup = createDaysMarkup(localDate);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(monthYearHeader);
        keyboard.add(weekDayRow);
        keyboard.addAll(daysMarkup);
        var calendarKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
       // return new KeyboardMarkupAppender(calendarKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
        return calendarKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createMonthYearRow(LocalDate localDate) {
        var prevMonthButton = createButton(PREV_MONTH.getCallback(), PREV_MONTH.name());
        var nextMonthButton = createButton(NEXT_MONTH.getCallback(), NEXT_MONTH.name());
        String monthYearPattern = "MMMM yyyy";
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(monthYearPattern, localeMessageSource.getLocale());
        String header = localDate.format(monthYearFormatter);
        var monthYearHeader = createButton(header);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(prevMonthButton);
        row.add(monthYearHeader);
        row.add(nextMonthButton);
        return row;
    }

    private List<InlineKeyboardButton> createWeekDayRow() {
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, localeMessageSource.getLocale())).
                forEach(day -> weekDayRow.add(createButton(day)));
        return weekDayRow;
    }

    private List<List<InlineKeyboardButton>> createDaysMarkup(LocalDate localDate) {
        LocalDate firstDayDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
        int shift = firstDayDate.getDayOfWeek().getValue() - 1;
        int columnCount = 7;
        int lengthOfMonth = localDate.lengthOfMonth();
        int numOfRows = (int) Math.ceil(((double) shift + lengthOfMonth) / columnCount);
        List<List<InlineKeyboardButton>> daysMarkup = new ArrayList<>();
        for (int index = 0; index < numOfRows; index++) {
            daysMarkup.add(buildRow(firstDayDate, shift, columnCount));
            firstDayDate = firstDayDate.plusDays(columnCount - shift);
            shift = 0;
        }
        return daysMarkup;
    }

    private List<InlineKeyboardButton> buildRow(LocalDate date, int shift, int columnCount) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        for (int index = 0; index < shift; index++) {
            row.add(createButton());
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                row.add(createButton(Integer.toString(day++), callbackDate.toString()));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(createButton());
            }
        }
        return row;
    }

    @Override
    public void addDataToKeyboard(String... args) {

    }
}
