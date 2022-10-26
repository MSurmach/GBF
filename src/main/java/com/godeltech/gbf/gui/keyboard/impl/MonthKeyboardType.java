package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.ControlKeyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CalendarBotButton.*;
import static com.godeltech.gbf.utils.DateUtils.formatYear;
import static com.godeltech.gbf.utils.KeyboardUtils.createButtonWithData;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class MonthKeyboardType implements KeyboardType {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.MONTH;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String date = callback.split(":")[1];
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        LocalDate callBackDate = LocalDate.parse(date);
        addYearHeader(callBackDate, keyboard);
        Month[] months = Month.values();
        for (var index = 0; index < months.length; ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != months.length) {
                String monthName = months[index].getDisplayName(TextStyle.FULL_STANDALONE, lms.getLocale());
                String monthLabel = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
                String monthCallback = LocalDate.of(callBackDate.getYear(), months[index], callBackDate.getDayOfMonth()).toString();
                buttonRow.add(KeyboardUtils.createButtonWithData(monthLabel, SELECT_MONTH, monthCallback));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.controlMarkup()).
                result();
    }

    private void addYearHeader(LocalDate date, List<List<InlineKeyboardButton>> keyboard) {
        var prevYearButton = createLocalButtonWithData(
                PREVIOUS, date.minusYears(1).toString(), lms);
        var nextYearButton = createLocalButtonWithData(
                NEXT, date.plusYears(1).toString(), lms);
        var yearHeader = createButtonWithData(
                formatYear(date), CHANGE_YEAR, date.toString());
        keyboard.add(List.of(prevYearButton, yearHeader, nextYearButton));
    }
}
