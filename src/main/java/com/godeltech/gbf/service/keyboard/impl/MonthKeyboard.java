package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.controls.Command.Calendar.*;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class MonthKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    public MonthKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
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
                String monthCallback = CHANGE_MONTH + ":" + LocalDate.of(callBackDate.getYear(), months[index], callBackDate.getDayOfMonth());
                buttonRow.add(createButton(monthLabel, monthCallback));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup monthKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(monthKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }

    private void addYearHeader(LocalDate date, List<List<InlineKeyboardButton>> keyboard) {
        var prevYearButton = createButton(
                PREVIOUS.getLocalDescription(localMessageSource),
                PREVIOUS + ":" + date.minusYears(1));
        var nextYearButton = createButton(
                NEXT.getLocalDescription(localMessageSource),
                NEXT + ":" + date.plusYears(1));
        String yearPattern = "yyyy";
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern(yearPattern);
        String header = date.format(yearFormatter);
        var yearHeader = createButton(
                header,
                SELECT_YEAR + ":" + date);
        keyboard.add(List.of(prevYearButton, yearHeader, nextYearButton));
    }
}
