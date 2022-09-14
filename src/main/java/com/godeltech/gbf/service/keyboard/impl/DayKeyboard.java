package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardAppender;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayKeyboard extends LocaleKeyboard implements KeybordAddData {

    private Keyboard controlKeyboard;
    private String year;
    private String monthName;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public DayKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        int month = Month.valueOf(monthName).getValue();
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), month);
        int countOfDays = yearMonth.lengthOfMonth();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int day = 1; day <= countOfDays; ) {
            int columnCount = 7;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (columnCount > 0 && day <= countOfDays) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(String.valueOf(day));
                button.setCallbackData(String.valueOf(day));
                rowWithButtons.add(button);
                columnCount--;
                day++;
            }
            keyboard.add(rowWithButtons);
        }
        InlineKeyboardMarkup dayKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardAppender(dayKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
    }

    @Override
    public void addDataToKeyboard(String... args) {
        if (args.length > 2) throw new IllegalArgumentException();
        year = args[0];
        monthName = args[1];
    }
}
