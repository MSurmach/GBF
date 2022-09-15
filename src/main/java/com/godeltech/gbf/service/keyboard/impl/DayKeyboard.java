package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayKeyboard extends LocaleKeyboard implements KeybordAddData {

    private Keyboard controlKeyboard;
    private String year;
    private String monthName;
    private final static String IGNORE = "IGNORE";

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public DayKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(getHeader(year));
        keyboard.add(getHeader(monthName));
        keyboard.add(getWeekDayRow());

        LocalDate givenDate = getLocalDate(year, monthName);
        var shift = givenDate.getDayOfWeek().getValue();
        var dayCount = givenDate.lengthOfMonth();

        for (var day = 1; day <= dayCount; day++) {
            var columnCount = 7;

        }

//
//        for (int day = 1; day <= countOfDays; ) {
//            int columnCount = 7;
//            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
//            while (columnCount > 0 && day <= countOfDays) {
//                InlineKeyboardButton button = new InlineKeyboardButton();
//                button.setText(String.valueOf(day));
//                button.setCallbackData(String.valueOf(day));
//                rowWithButtons.add(button);
//                columnCount--;
//                day++;
//            }
//            keyboard.add(rowWithButtons);
//        }
        InlineKeyboardMarkup dayKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        //     return new KeyboardAppender(dayKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
        return dayKeyboardMarkup;
    }

    @Override
    public void addDataToKeyboard(String... args) {
        if (args.length > 2) throw new IllegalArgumentException();
        year = args[0];
        monthName = args[1];
    }

    private List<InlineKeyboardButton> getWeekDayRow() {
        final String[] weekDay = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        List<InlineKeyboardButton> weekDayRow = new ArrayList<>();
        for (String day : weekDay) {
            var button = new InlineKeyboardButton(day);
            button.setCallbackData(IGNORE);
        }
        return weekDayRow;
    }

    private List<InlineKeyboardButton> getHeader(String content) {
        var button = new InlineKeyboardButton(content);
        button.setCallbackData(content);
        return List.of(button);
    }

    private LocalDate getLocalDate(String year, String monthName) {
        var month = Month.valueOf(monthName);
        var digits = Integer.parseInt(year);
        var firstDay = 1;
        return LocalDate.of(digits, month, firstDay);
    }
}
