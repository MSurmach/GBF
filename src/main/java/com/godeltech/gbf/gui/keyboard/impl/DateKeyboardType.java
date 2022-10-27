package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.gui.button.CalendarBotButton.*;
import static com.godeltech.gbf.utils.ButtonUtils.*;
import static com.godeltech.gbf.utils.DateUtils.formatMonth;
import static com.godeltech.gbf.utils.DateUtils.formatYear;
import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
public class DateKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.DATE;
    }

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
//        if (userData.getTempRoutePoint().getStartDate() != null)
//            addDateControls(keyboard);
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private void addDateControls(List<List<InlineKeyboardButton>> keyboard) {
        keyboard.add(List.of(
                createLocalButton(CLEAR_DATE_SELECT, lms),
                createLocalButton(CONFIRM_DATE, lms)));
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
        var yearButton = createButtonWithData(formatYear(date), CHANGE_YEAR, date.toString());
        var prevMonthButton = createLocalButtonWithData(PREVIOUS, date.minusMonths(1).toString(), lms);
        var nextMonthButton = createLocalButtonWithData(NEXT, date.plusMonths(1).toString(), lms);
        String formattedMonth = formatMonth(date, lms.getLocale());
        String monthLabel = formattedMonth.substring(0, 1).toUpperCase() + formattedMonth.substring(1);
        var monthButton = createButtonWithData(monthLabel, CHANGE_MONTH, date.toString());
        keyboard.add(List.of(yearButton));
        keyboard.add(List.of(prevMonthButton, monthButton, nextMonthButton));
    }

    private void addWeekDayRow(List<List<InlineKeyboardButton>> keyboard) {
        final String dayOfWeekCallback = "dayOfWeek";
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, lms.getLocale())).
                forEach(day -> weekDayRow.add(createButtonWithData(day, IGNORE, dayOfWeekCallback)));
        keyboard.add(weekDayRow);
    }

    private List<InlineKeyboardButton> buildDayRow(LocalDate date, int shift, int columnCount) {
        final String emptyLabel = "  ";
        final String emptyDayCallback = "emptyDay";
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        var serviceButton = createButtonWithData(emptyLabel, IGNORE, emptyDayCallback);
        for (int index = 0; index < shift; index++) {
            row.add(serviceButton);
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                row.add(createButtonWithData(Integer.toString(day++), SELECT_DAY, callbackDate.toString()));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(serviceButton);
            }
        }
        return row;
    }
}
