package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Country;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.LocaleKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.service.keyboard.util.KeyboardUtils.createButton;

@Service
public class CityKeyboard extends LocaleKeyboard {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public CityKeyboard(LocalMessageSource localMessageSource) {
        super(localMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(String callback) {
        Country country = Country.valueOf(callback.toUpperCase());
        List<String> cities = country.getCities();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < cities.size(); ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != cities.size()) {
                String buttonCallback = cities.get(index);
                String label = country.getCities(localMessageSource).get(index);
                buttonRow.add(createButton(label, buttonCallback));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup(null)).result();
    }
}
