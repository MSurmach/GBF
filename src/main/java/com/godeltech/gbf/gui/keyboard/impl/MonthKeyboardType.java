package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.utils.ButtonUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.CalendarBotButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createButtonWithData;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButtonWithData;
import static com.godeltech.gbf.gui.utils.DateUtils.formatYear;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
@Slf4j
public class MonthKeyboardType implements KeyboardType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.MONTH;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        String callback = session.getCallbackHistory().peek();
        String date = callback.split(":")[1];
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        LocalDate callBackDate = LocalDate.parse(date);
        log.debug("Create month keyboard type for session data with user: {} and with income date : {}",
                session.getTelegramUser(), callBackDate);
        addYearHeader(callBackDate, keyboard, lms);
        Month[] months = Month.values();
        for (var index = 0; index < months.length; ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != months.length) {
                String monthName = months[index].getDisplayName(TextStyle.FULL_STANDALONE, lms.getLocale());
                String monthLabel = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
                String monthCallback = LocalDate.of(callBackDate.getYear(), months[index], callBackDate.getDayOfMonth()).toString();
                buttonRow.add(ButtonUtils.createButtonWithData(monthLabel, SELECT_MONTH, monthCallback));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private void addYearHeader(LocalDate date, List<List<InlineKeyboardButton>> keyboard, LocalMessageSource lms) {
        var prevYearButton = createLocalButtonWithData(
                PREVIOUS, date.minusYears(1).toString(), lms);
        var nextYearButton = createLocalButtonWithData(
                NEXT, date.plusYears(1).toString(), lms);
        var yearHeader = createButtonWithData(
                formatYear(date), CHANGE_YEAR, date.toString());
        keyboard.add(List.of(prevYearButton, yearHeader, nextYearButton));
    }
}
