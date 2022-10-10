package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CalendarBotButton.*;

@Component
@AllArgsConstructor
public class MonthKeyboard implements Keyboard {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource localMessageSource;

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
                String monthName = months[index].getDisplayName(TextStyle.FULL_STANDALONE, localMessageSource.getLocale());
                String monthLabel = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
                String monthCallback = SELECT_MONTH + ":" + LocalDate.of(callBackDate.getYear(), months[index], callBackDate.getDayOfMonth());
                buttonRow.add(KeyboardUtils.createLocalButton(monthLabel, monthCallback));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup monthKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(monthKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(userData)).result();
    }

    private void addYearHeader(LocalDate date, List<List<InlineKeyboardButton>> keyboard) {
        var prevYearButton = KeyboardUtils.createButton(
                PREVIOUS.localLabel(localMessageSource),
                PREVIOUS + ":" + date.minusYears(1));
        var nextYearButton = KeyboardUtils.createButton(
                NEXT.localLabel(localMessageSource),
                NEXT + ":" + date.plusYears(1));
        String yearPattern = "yyyy";
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern(yearPattern);
        String header = date.format(yearFormatter);
        var yearHeader = KeyboardUtils.createLocalButton(
                header,
                CHANGE_YEAR + ":" + date);
        keyboard.add(List.of(prevYearButton, yearHeader, nextYearButton));
    }
}
