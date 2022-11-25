package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.gui.button.CalendarBotButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.*;
import static com.godeltech.gbf.gui.utils.ConstantUtil.DATE_MARKER;
import static com.godeltech.gbf.gui.utils.DateUtils.formatMonth;
import static com.godeltech.gbf.gui.utils.DateUtils.formatYear;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
@Slf4j
public class DateKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.DATE;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        String callback = session.getCallbackHistory().peek();
        LocalDate date;
        try {
            String callbackDate = callback.split(":")[1];
            date = LocalDate.parse(callbackDate);
            log.debug("Create date keyboard type for session data with user: {} and income date: {}",
                    session.getTelegramUser(), date);
        } catch (IndexOutOfBoundsException exception) {
            date = LocalDate.now();
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(yearRow(date));
        keyboard.add(monthWithPaginationRow(date, lms));
        keyboard.add(daysOfWeekRow(lms));
        keyboard.addAll(calendarMarkup(date, session, lms));
        keyboard.add(List.of(createLocalButton(CONFIRM_DATE, lms)));
        if (session.getTempStartDate() != null) {
            keyboard.add(List.of(createLocalButton(CLEAR_DATES, lms)));
        }
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> yearRow(LocalDate date) {
        var yearButton = createButtonWithData(formatYear(date), CHANGE_YEAR, date.toString());
        return List.of(yearButton);
    }


    private List<InlineKeyboardButton> monthWithPaginationRow(LocalDate date, LocalMessageSource lms) {
        var prevMonthButton = createLocalButtonWithData(PREVIOUS, date.minusMonths(1).toString(), lms);
        var nextMonthButton = createLocalButtonWithData(NEXT, date.plusMonths(1).toString(), lms);
        String formattedMonth = formatMonth(date, lms.getLocale());
        String monthLabel = formattedMonth.substring(0, 1).toUpperCase() + formattedMonth.substring(1);
        var monthButton = createButtonWithData(monthLabel, CHANGE_MONTH, date.toString());
        return List.of(prevMonthButton, monthButton, nextMonthButton);
    }

    private List<InlineKeyboardButton> daysOfWeekRow(LocalMessageSource lms) {
        final String dayOfWeekCallback = "dayOfWeek";
        List<InlineKeyboardButton> daysOfWeekRow = new ArrayList<>();
        Arrays.stream(DayOfWeek.values()).
                map(day -> day.getDisplayName(TextStyle.SHORT, lms.getLocale())).
                forEach(day -> daysOfWeekRow.add(createButtonWithData(day, IGNORE, dayOfWeekCallback)));
        return daysOfWeekRow;
    }

    private List<List<InlineKeyboardButton>> calendarMarkup(LocalDate localDate, Session session, LocalMessageSource lms) {
        List<List<InlineKeyboardButton>> calendarMarkup = new ArrayList<>();
        LocalDate firstDayDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
        int shift = firstDayDate.getDayOfWeek().getValue() - 1;
        int columnCount = 7;
        int lengthOfMonth = localDate.lengthOfMonth();
        int numOfRows = (int) Math.ceil(((double) shift + lengthOfMonth) / columnCount);
        for (int index = 0; index < numOfRows; index++) {
            calendarMarkup.add(buildDaysRow(firstDayDate, shift, columnCount, session, lms));
            firstDayDate = firstDayDate.plusDays(columnCount - shift);
            shift = 0;
        }
        return calendarMarkup;

    }

    private List<InlineKeyboardButton> buildDaysRow(LocalDate date, int shift, int columnCount, Session session, LocalMessageSource lms) {
        final String emptyLabel = "  ";
        final String emptyDayCallback = "emptyDay";
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        var serviceButton = createButtonWithData(emptyLabel, IGNORE, emptyDayCallback);
        for (int index = 0; index < shift; index++) {
            row.add(serviceButton);
        }
        for (int index = shift; index < columnCount; index++) {
            if (day <= date.lengthOfMonth()) {
                var dayButton = (Objects.equals(date, session.getTempStartDate()) || Objects.equals(date, session.getTempEndDate())) ?
                        createLocalButtonWithData(DATE_MARKER, SELECT_DAY, date.toString(), lms) :
                        createButtonWithData(Integer.toString(day), SELECT_DAY, date.toString());
                day++;
                row.add(dayButton);
                date = date.plusDays(1);
            } else {
                row.add(serviceButton);
            }
        }
        return row;
    }
}
