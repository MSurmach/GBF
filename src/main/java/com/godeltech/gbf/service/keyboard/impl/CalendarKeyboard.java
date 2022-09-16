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
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createMonthYearRow(localDate));
        keyboard.add(createWeekDayRow());
        keyboard.addAll(createDaysMarkup(localDate));
        var calendarKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(calendarKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
    }

    private List<InlineKeyboardButton> createMonthYearRow(LocalDate localDate) {
        var prevMonthButton = createButton(PREV_MONTH.getCallback(), PREV_MONTH.name());
        var nextMonthButton = createButton(NEXT_MONTH.getCallback(), NEXT_MONTH.name());
        String monthYearPattern = "MMMM yyyy";
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(monthYearPattern, localeMessageSource.getLocale());
        String header = localDate.format(monthYearFormatter);
        var monthYearHeader = createButton(header);
        return List.of(prevMonthButton, monthYearHeader, nextMonthButton);
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
        for (int row = 0, day = 1; row < numOfRows; row++) {
            List<InlineKeyboardButton> rowOfButton = new ArrayList<>();
            for (int column = 0; column < columnCount; column++) {
                if (shift-- > 0 || day > lengthOfMonth) rowOfButton.add(createButton());
                else {
                    String callback = dateCallback(day, localDate);
                    String label = String.valueOf(day);
                    day++;
                    rowOfButton.add(createButton(label, callback));
                }
            }
            daysMarkup.add(rowOfButton);
        }
        return daysMarkup;
    }

    private String dateCallback(int day, LocalDate localDate) {
        String datePattern = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.of(localDate.getYear(), localDate.getMonth(), day).format(formatter);
    }

    @Override
    public void addDataToKeyboard(String... args) {

    }
}
