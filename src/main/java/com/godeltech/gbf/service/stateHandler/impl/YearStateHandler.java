package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Calendar;
import java.util.List;

public class YearStateHandler implements BotStateHandler {

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        InlineKeyboardButton currentYearButton = new InlineKeyboardButton(String.valueOf(currentYear));
        InlineKeyboardButton nextYearButton = new InlineKeyboardButton(String.valueOf(++currentYear));
        List<InlineKeyboardButton> buttons = List.of(currentYearButton, nextYearButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }

    @Override
    public SendMessage handleUpdate(Update update) {
        return null;
    }
}
