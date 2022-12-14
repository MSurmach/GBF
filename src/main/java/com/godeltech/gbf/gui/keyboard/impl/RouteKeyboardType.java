package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.utils.ButtonUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.godeltech.gbf.gui.button.RouteButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;

@Component
@AllArgsConstructor
@Slf4j
public class RouteKeyboardType implements KeyboardType {

    private final LocalMessageSourceFactory localMessageSourceFactory;
    private CityService cityService;

    @Override
    public State getState() {
        return State.ROUTE;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        log.debug("Create route keyboard type for session data with user: {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        List<City> allCities = cityService.findAll();
        LinkedList<RoutePoint> route = session.getTempRoute();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (var index = 0; index < allCities.size(); ) {
            var columnCount = 3;
            List<InlineKeyboardButton> citiesRow = new ArrayList<>();
            while (columnCount > 0 && index != allCities.size()) {
                City city = allCities.get(index);
                Optional<RoutePoint> found = route.stream().filter(routePoint -> routePoint.getCity().equals(city)).findFirst();
                String statusLabel = found.map(routePoint -> statusLabel(routePoint, lms)).orElse(null);
                String cityName = city.getName();
                citiesRow.add(ButtonUtils.createLocalButtonWithData(statusLabel, cityName, SELECT_CITY, cityName, lms));
                columnCount--;
                index++;
            }
            keyboard.add(citiesRow);
        }
        if (!route.isEmpty())
            keyboard.add(routeControlsRow(lms));
        InlineKeyboardMarkup countryKeyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(countryKeyboardMarkup).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> routeControlsRow(LocalMessageSource lms) {
        var clearButton = createLocalButton(CLEAR_ROUTE, lms);
        var confirmRouteButton = createLocalButton(CONFIRM_ROUTE, lms);
        return List.of(clearButton, confirmRouteButton);
    }

    private String statusLabel(RoutePoint routePoint, LocalMessageSource lms) {
        final String INITIAL_STATUS_LABEL_CODE = "start.flag";
        final String FINAL_STATUS_LABEL_CODE = "end.flag";
        return switch (routePoint.getStatus()) {
            case INITIAL -> lms.getLocaleMessage(INITIAL_STATUS_LABEL_CODE);
            case INTERMEDIATE -> lms.getLocaleMessage(String.valueOf(routePoint.getOrderNumber()));
            case FINAL -> lms.getLocaleMessage(FINAL_STATUS_LABEL_CODE);
        };
    }
}
