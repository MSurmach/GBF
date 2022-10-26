package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.model.db.Status;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.godeltech.gbf.gui.button.FormButton.*;
import static com.godeltech.gbf.model.db.Status.*;
import static com.godeltech.gbf.utils.ButtonUtils.createLocalButton;

@Component
public class FormKeyboardType implements KeyboardType {

    private final LocalMessageSource lms;

    private final SuccessRegistrationKeyboardType backMenuKeyboard;
    private final Map<FormButton, InlineKeyboardButton> buttons = new HashMap<>();

    public FormKeyboardType(LocalMessageSource lms, SuccessRegistrationKeyboardType backMenuKeyboard) {
        this.lms = lms;
        this.backMenuKeyboard = backMenuKeyboard;
        initializeButtons();
    }

    private void initializeButtons() {
        Arrays.stream(FormButton.values()).
                forEach(formButton ->
                        buttons.put(formButton, createLocalButton(formButton, lms)));
    }

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(UserData userData) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        LinkedList<RoutePoint> points = userData.getRoutePoints();
        var initialRoutePointRow = routePointWithStatusExists(points, INITIAL) ?
                List.of(buttons.get(EDIT_INITIAL_ROUTE_POINT)) :
                List.of(buttons.get(ADD_INITIAL_ROUTE_POINT));
        keyboard.add(initialRoutePointRow);
        var finalRoutePointRow = routePointWithStatusExists(points, FINAL) ?
                List.of(buttons.get(EDIT_FINAL_ROUTE_POINT)) :
                List.of(buttons.get(ADD_FINAL_ROUTE_POINT));
        keyboard.add(finalRoutePointRow);
        if (routePointWithStatusExists(points, INITIAL) &&
                routePointWithStatusExists(points, FINAL) &&
                (userData.getRole() == Role.COURIER || userData.getRole() == Role.REGISTRATIONS_VIEWER)) {
            var intermediateRoutePointRow = List.of(buttons.get(ADD_INTERMEDIATE_ROUTE_POINT));
            keyboard.add(intermediateRoutePointRow);
        }
        if (routePointWithStatusExists(points, INTERMEDIATE))
            keyboard.add(List.of(buttons.get(INTERMEDIATE_EDITOR)));
        keyboard.add(commentButton(userData));
        keyboard.add(cargoButton(userData));
        keyboard.add(confirmButton(userData.getRole()));
        return new KeyboardMarkupAppender().
                append(new InlineKeyboardMarkup(keyboard)).
                append(backMenuKeyboard.getKeyboardMarkup(userData)).
                result();
    }

    private boolean routePointWithStatusExists(List<RoutePoint> points, Status status) {
        return points.stream().anyMatch(routePoint -> routePoint.getStatus() == status);
    }

    private List<InlineKeyboardButton> commentButton(UserData userData) {
        return userData.getComment() == null ?
                List.of(buttons.get(ADD_COMMENT)) :
                List.of(buttons.get(EDIT_COMMENT), buttons.get(DELETE_COMMENT));
    }

    private List<InlineKeyboardButton> cargoButton(UserData userData) {
        return userData.isCargoEmpty() ?
                List.of(buttons.get(ADD_CARGO)) :
                List.of(buttons.get(EDIT_CARGO));
    }

    private List<InlineKeyboardButton> confirmButton(Role role) {
        return switch (role) {
            case COURIER -> List.of(buttons.get(FORM_REGISTER));
            case CLIENT -> List.of(buttons.get(FORM_SEARCH));
            case REGISTRATIONS_VIEWER, REQUESTS_VIEWER -> List.of(buttons.get(FORM_EDIT_CONFIRM));
        };
    }
}
