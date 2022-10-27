package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.button.RoutePointFormButton;
import com.godeltech.gbf.utils.KeyboardUtils;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.godeltech.gbf.gui.button.RoutePointFormButton.*;
import static com.godeltech.gbf.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;

@Component
public class RoutePointFormKeyboardType implements KeyboardType {
    private final LocalMessageSource lms;
    private final Map<RoutePointFormButton, InlineKeyboardButton> buttons = new HashMap<>();

    public RoutePointFormKeyboardType(LocalMessageSource lms) {
        this.lms = lms;
        initializeButtons();
    }

    private void initializeButtons() {
        Arrays.stream(RoutePointFormButton.values()).
                forEach(formButton ->
                        buttons.put(formButton, createLocalButton(formButton, lms)));
    }

    @Override
    public State getState() {
        return State.ROUTE_POINT_FORM;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(countryButtonRow(tempRoutePoint));
        keyboard.add(cityButtonRow(tempRoutePoint));
        keyboard.add(visitDateButtonRow(tempRoutePoint));
        keyboard.add(confirmButtonRow());
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> confirmButtonRow() {
        return List.of(buttons.get(SAVE));
    }

    private List<InlineKeyboardButton> countryButtonRow(RoutePoint routePoint) {
//        return routePoint.getCountry() == null ?
//                List.of(buttons.get(ADD_COUNTRY)) :
//                List.of(buttons.get(EDIT_COUNTRY));
        return null;
    }

    private List<InlineKeyboardButton> cityButtonRow(RoutePoint routePoint) {
        return routePoint.getCity() == null ?
                List.of(buttons.get(ADD_CITY)) :
                List.of(buttons.get(EDIT_CITY), buttons.get(DELETE_CITY));
    }

    private List<InlineKeyboardButton> visitDateButtonRow(RoutePoint routePoint) {
//        return routePoint.getStartDate() == null ?
//                List.of(buttons.get(ADD_VISIT_DATE)) :
//                List.of(buttons.get(EDIT_VISIT_DATE), buttons.get(DELETE_VISIT_DATE));
        return null;
    }
}
