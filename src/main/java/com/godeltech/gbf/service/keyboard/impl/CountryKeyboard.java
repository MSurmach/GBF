package com.godeltech.gbf.service.keyboard.impl;

import com.godeltech.gbf.LocaleMessageSource;
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

@Service
public class CountryKeyboard extends LocaleKeyboard {
    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public CountryKeyboard(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup() {
        Country[] countries = Country.values();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < countries.length; ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != countries.length) {
                var button = new InlineKeyboardButton();
                button.setText(countries[index].getCountryName(localeMessageSource));
                button.setCallbackData(countries[index].name());
                buttonRow.add(button);
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(controlKeyboard.getKeyboardMarkup()).result();
    }
}
