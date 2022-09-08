package com.godeltech.gbf.keyboard.impl;

import com.godeltech.gbf.model.Country;
import com.godeltech.gbf.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

public class CountryKeyboard implements Keyboard {
    private final Country country = Country.UK;

    @Override
    public InlineKeyboardMarkup getKeyBoardMarkup() {
        return new InlineKeyboardMarkup(List.of(createButtons(country)));
    }

    private List<InlineKeyboardButton> createButtons(Country countries) {
        List<Country> countryList = List.of(country.values());
        return countryList.stream().
                sorted().
                map(country -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(country.name());
                    button.setCallbackData("/google.com");
                    return button;
                }).
                collect(Collectors.toList());
    }
}
