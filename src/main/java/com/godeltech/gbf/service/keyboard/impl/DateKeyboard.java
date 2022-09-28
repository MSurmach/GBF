package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.util.KeyboardUtils;
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

import static com.godeltech.gbf.management.button.BotButton.Calendar.*;

@Service
@AllArgsConstructor
public class DateKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String callback = userData.getCallback();
        String givenDate = callback.split(":")[1];
        LocalDate date = LocalDate.parse(givenDate);
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
        var prevMonthButton = KeyboardUtils.createButton(
                PREVIOUS.getLocalMessage(localMessageSource),
                PREVIOUS + ":" + date.minusMonths(1));
        var nextMonthButton = KeyboardUtils.createButton(
                NEXT.getLocalMessage(localMessageSource),
                NEXT + ":" + date.plusMonths(1));
        String monthYearPattern = "LLLL yyyy";
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(monthYearPattern).withLocale(localMessageSource.getLocale());
        String header = date.format(monthYearFormatter).substring(0, 1).toUpperCase() + date.format(monthYearFormatter).substring(1);
        var monthYearHeader = KeyboardUtils.createButton(
                header,
                SELECT_MONTH + ":" + date);
        keyboard.add(List.of(prevMonthButton, monthYearHeader, nextMonthButton));
    }

    private void addWeekDayRow(List<List<InlineKeyboardButton>> keyboard) {
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, localMessageSource.getLocale())).
                forEach(day -> weekDayRow.add(KeyboardUtils.createButton(day)));
        keyboard.add(weekDayRow);
    }

    private List<InlineKeyboardButton> buildDayRow(LocalDate date, int shift, int columnCount) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        for (int index = 0; index < shift; index++) {
            row.add(KeyboardUtils.createButton());
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                row.add(KeyboardUtils.createButton(Integer.toString(day++), SELECT_DAY + ":" + callbackDate));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(KeyboardUtils.createButton());
            }
        }
        return row;
    }
}
