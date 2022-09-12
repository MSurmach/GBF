package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.model.Country;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CountryStateHandler implements BotStateHandler {


    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<Country> countryList = List.of(Country.values());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < countryList.size(); ) {
            int countriesInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (countriesInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(countryList.get(index).name());
                button.setCallbackData(countryList.get(index).name());
                rowWithButtons.add(button);
                countriesInRow--;
                index++;
                if (index == countryList.size()) break;
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
