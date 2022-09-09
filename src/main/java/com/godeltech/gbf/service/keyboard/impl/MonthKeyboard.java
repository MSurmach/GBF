package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.service.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MonthKeyboard implements Keyboard {

    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
        dateFormatSymbols.getMonths();

        Calendar calendar = Calendar.getInstance();
        calendar.getDisplayNames()
        return null;
    }
}
