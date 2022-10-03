package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import lombok.AllArgsConstructor;
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

import static com.godeltech.gbf.management.button.CalendarBotButton.*;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
@AllArgsConstructor
public class DateKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        LocalDate date;
        try {
            String callbackDate = callback.split(":")[1];
            date = LocalDate.parse(callbackDate);
        } catch (IndexOutOfBoundsException exception) {
            date = LocalDate.now();
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        addMonthYear(date, keyboard);
        addWeekDayRow(keyboard);
        addDayRows(date, keyboard);
        var calendarKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(calendarKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
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
        String yearPattern = "yyyy";
        String monthPattern = "LLLL";
        var yearButton = createButton(
                DateTimeFormatter.ofPattern(yearPattern).format(date),
                CHANGE_YEAR + ":" + date);
        var prevMonthButton = createButton(
                PREVIOUS.localLabel(localMessageSource),
                PREVIOUS + ":" + date.minusMonths(1));
        var nextMonthButton = createButton(
                NEXT.localLabel(localMessageSource),
                NEXT + ":" + date.plusMonths(1));
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(monthPattern).withLocale(localMessageSource.getLocale());
        String month = date.format(monthFormatter).substring(0, 1).toUpperCase() + date.format(monthFormatter).substring(1);
        var monthButton = createButton(
                month,
                CHANGE_MONTH + ":" + date);
        keyboard.add(List.of(yearButton));
        keyboard.add(List.of(prevMonthButton, monthButton, nextMonthButton));
    }

    private void addWeekDayRow(List<List<InlineKeyboardButton>> keyboard) {
        final String dayOfWeekCallback = "dayOfWeek";
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, localMessageSource.getLocale())).
                forEach(day -> weekDayRow.add(createButton(day, IGNORE + ":" + dayOfWeekCallback)));
        keyboard.add(weekDayRow);
    }

    private List<InlineKeyboardButton> buildDayRow(LocalDate date, int shift, int columnCount) {
        final String emptyLabel = " ";
        final String emptyDayCallback = "emptyDay";
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        InlineKeyboardButton serviceButton = createButton(emptyLabel, IGNORE + ":" + emptyDayCallback);
        for (int index = 0; index < shift; index++) {
            row.add(serviceButton);
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                row.add(createButton(Integer.toString(day++), SELECT_DAY + ":" + callbackDate));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(serviceButton);
            }
        }
        return row;
    }
}
