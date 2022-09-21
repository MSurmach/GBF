package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.model.CalendarCommand.RETURNEDYEAR;
import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class YearKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public YearKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        String givenDate = callback.split(":")[1];
        LocalDate[] years = getYearsArray(4);
        List<InlineKeyboardButton> yearButtons = Arrays.stream(years)
                .map(date -> {
                    String buttonCallback = RETURNEDYEAR.name() + ":" + date;
                    return createButton(Integer.toString(date.getYear()), buttonCallback);
                }).toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(yearButtons);
        InlineKeyboardMarkup yearKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(yearKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }

    private LocalDate[] getYearsArray(int countYear) {
        LocalDate startDate = LocalDate.now();
        LocalDate[] result = new LocalDate[countYear];
        for (int index = 0; index < countYear; index++) {
            result[index] = startDate.plusYears(index);
        }
        return result;
    }
}
