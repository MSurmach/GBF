package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.godeltech.gbf.gui.button.CityButton.SELECT_CITY;
import static com.godeltech.gbf.utils.ButtonUtils.createLocalButtonWithData;
import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;
import static com.godeltech.gbf.utils.KeyboardUtils.confirmMarkup;

@Component
@AllArgsConstructor
public class CityKeyboardType implements KeyboardType {

    private LocalMessageSource lms;
    private CityService cityService;

    @Override
    public State getState() {
        return State.CITY;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<City> allCities = cityService.findAll();
        LinkedList<RoutePoint> userRoutePoints = userData.getRoutePoints();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < allCities.size(); ) {
            var columnCount = 3;
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            while (columnCount > 0 && index != allCities.size()) {
                City city = allCities.get(index);
                Optional<RoutePoint> found = userRoutePoints.stream().filter(routePoint -> routePoint.getCity().equals(city)).findFirst();
                String statusLabel = found.map(this::statusLabel).orElse(null);
                String cityName = city.getName();
                buttonRow.add(createLocalButtonWithData(statusLabel, cityName, SELECT_CITY, cityName, lms));
                columnCount--;
                index++;
            }
            keyboard.add(buttonRow);
        }
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).
                append(confirmMarkup(lms)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private String statusLabel(RoutePoint routePoint) {
        final String INITIAL_STATUS_LABEL_CODE = "start.flag";
        final String FINAL_STATUS_LABEL_CODE = "end.flag";
        return switch (routePoint.getStatus()) {
            case INITIAL -> lms.getLocaleMessage(INITIAL_STATUS_LABEL_CODE);
            case INTERMEDIATE -> lms.getLocaleMessage(String.valueOf(routePoint.getOrderNumber()));
            case FINAL -> lms.getLocaleMessage(FINAL_STATUS_LABEL_CODE);
        };
    }
}
