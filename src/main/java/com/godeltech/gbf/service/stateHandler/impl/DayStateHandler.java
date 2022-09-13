package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayStateHandler implements BotStateHandler {

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        return null;
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        YearMonth yearMonth = YearMonth.of(2022, 9);
        int countOfDays = yearMonth.lengthOfMonth();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int day = 1; day <= countOfDays; ) {
            int daysInRow = 7;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (daysInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(String.valueOf(day));
                button.setCallbackData(String.valueOf(day));
                rowWithButtons.add(button);
                daysInRow--;
                day++;
                if (day > countOfDays) break;
            }
            keyboard.add(rowWithButtons);
        }
        return new InlineKeyboardMarkup(keyboard);
    }
}
