package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthStateHandler implements BotStateHandler {

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<String> months = Arrays.asList(DateFormatSymbols.getInstance().getMonths());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < months.size(); ) {
            int monthsInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (monthsInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(months.get(index).substring(0, 2));
                button.setCallbackData(months.get(index));
                rowWithButtons.add(button);
                monthsInRow--;
                index++;
                if (index == months.size()) break;
            }
            keyboard.add(rowWithButtons);
        }
        return new InlineKeyboardMarkup(keyboard);
    }

    @Override
    public SendMessage handleUpdate(Update update) {
        return null;
    }
}
