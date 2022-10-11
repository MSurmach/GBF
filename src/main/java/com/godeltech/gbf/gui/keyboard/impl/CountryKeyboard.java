package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.service.country.CountryService;
import com.godeltech.gbf.utils.KeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CountryKeyboard implements Keyboard {
    private ControlKeyboard controlKeyboard;
    private LocalMessageSource lms;

    private CountryService countryService;


    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<Country> countries = countryService.findAll();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < countries.size(); ) {
            var columnCount = 2;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != countries.size()) {
                String countryName = countries.get(index).getName();
                buttonRow.add(KeyboardUtils.createLocalButton(countryName, countryName, lms));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }
}
