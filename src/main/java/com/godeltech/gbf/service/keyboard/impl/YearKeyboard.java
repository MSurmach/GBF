package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.service.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YearKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        var calendar = Calendar.getInstance();
        var currentYear = calendar.get(Calendar.YEAR);
        var currentYearButton = new InlineKeyboardButton(String.valueOf(currentYear));
        var nextYearButton = new InlineKeyboardButton(String.valueOf(++currentYear));
        var years = new ArrayList<InlineKeyboardButton>();
        years.add(currentYearButton);
        years.add(nextYearButton);
        return new InlineKeyboardMarkup(List.of(years));
    }
}
