package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.IntermediateEditorButton.*;
import static com.godeltech.gbf.model.db.Status.INTERMEDIATE;
import static com.godeltech.gbf.utils.DateUtils.shortFormatDate;
import static com.godeltech.gbf.utils.KeyboardUtils.createButtonWithData;
import static com.godeltech.gbf.utils.KeyboardUtils.createLocalButtonWithData;

@Component
@AllArgsConstructor
public class IntermediateEditorKeyboard implements Keyboard {
    private final LocalMessageSource lms;
    private final ControlKeyboard controlKeyboard;

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<RoutePoint> intermediateRoutePoints = userData.getRoutePoints().stream().
                filter(routePoint -> routePoint.getStatus() == INTERMEDIATE).
                toList();
        if (intermediateRoutePoints.size() == 1) {
            RoutePoint intermediateRoutePoint = intermediateRoutePoints.get(0);
            keyboard.add(buildEditButtonRow(intermediateRoutePoint));
            keyboard.add(buildDeleteButtonRow(intermediateRoutePoint));
        } else intermediateRoutePoints.forEach(
                intermediateRoutePoint -> {
                    keyboard.add(buildEditButtonRow(intermediateRoutePoint));
                    keyboard.add(buildDeleteWithOrderButtonsRow(intermediateRoutePoint));
                }
        );
        keyboard.add(List.of(createLocalButtonWithData(SAVE_CHANGES, "-1", lms)));
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(controlKeyboard.getKeyboardMarkup(userData)).
                result();
    }

    private List<InlineKeyboardButton> buildEditButtonRow(RoutePoint routePoint) {
        String order = String.valueOf(routePoint.getOrderNumber());
        String localLabel = lms.getLocaleMessage(EDIT_INTERMEDIATE_ROUTE_POINT.name(), buildEditLabelContent(routePoint));
        var editButton = createButtonWithData(
                localLabel,
                EDIT_INTERMEDIATE_ROUTE_POINT,
                order
        );
        return List.of(editButton);
    }

    private List<InlineKeyboardButton> buildDeleteWithOrderButtonsRow(RoutePoint routePoint) {
        String order = String.valueOf(routePoint.getOrderNumber());
        var deleteButton = createLocalButtonWithData(DELETE_ROUTE_POINT, order, lms);
        var orderUpButton = createLocalButtonWithData(ORDER_UP, order, lms);
        var orderDownButton = createLocalButtonWithData(ORDER_DOWN, order, lms);
        return List.of(orderDownButton, deleteButton, orderUpButton);
    }

    private List<InlineKeyboardButton> buildDeleteButtonRow(RoutePoint routePoint) {
        String order = String.valueOf(routePoint.getOrderNumber());
        var deleteButton = createLocalButtonWithData(DELETE_ROUTE_POINT, order, lms);
        return List.of(deleteButton);
    }

    private String buildEditLabelContent(RoutePoint routePoint) {
        String countryName = routePoint.getCountry().getName();
        String localCountryName = lms.getLocaleMessage(countryName);
        StringBuilder labelBuilder = new StringBuilder(localCountryName);
        City city = routePoint.getCity();
        if (city != null) {
            String localCityName = lms.getLocaleMessage(city.getName());
            labelBuilder.append("/").append(localCityName);
        }
        LocalDate visitDate = routePoint.getVisitDate();
        if (visitDate != null) {
            labelBuilder.append("/").append(shortFormatDate(visitDate));
        }
        return labelBuilder.toString();
    }
}
