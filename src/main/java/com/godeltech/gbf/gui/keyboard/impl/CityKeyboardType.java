package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.ControlKeyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class CityKeyboardType implements KeyboardType {

    private ControlKeyboard controlKeyboard;
    private LocalMessageSource lms;

    private CityService cityService;

    @Override
    public State getState() {
        return State.CITY;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        Country country = userData.getTempRoutePoint().getCountry();
        List<City> cities = cityService.findCitiesByCountry(country);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < cities.size(); ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != cities.size()) {
                City city = cities.get(index);
                String cityName = city.getName();
                String cityId = city.getId().toString();
                buttonRow.add(createLocalButtonWithData(cityName, cityName, cityId, lms));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).append(controlKeyboard.controlMarkup()).result();
    }
}
