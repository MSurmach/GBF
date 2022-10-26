package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.utils.ButtonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.godeltech.gbf.gui.button.CalendarBotButton.SELECT_YEAR;
import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
public class YearKeyboardType implements KeyboardType {

    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.YEAR;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        LocalDate[] years = getYearsArray(4);
        List<InlineKeyboardButton> yearButtons = Arrays.stream(years).
                map(date -> ButtonUtils.createButtonWithData(Integer.toString(date.getYear()), SELECT_YEAR, date.toString())).
                toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(yearButtons);
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
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
