package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.button.FormButton;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.godeltech.gbf.gui.button.FormButton.*;
import static com.godeltech.gbf.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.utils.KeyboardUtils.backAndMenuMarkup;

@Component
public class FormKeyboardType implements KeyboardType {

    private final LocalMessageSource lms;
    private final Map<FormButton, InlineKeyboardButton> buttons = new HashMap<>();

    public FormKeyboardType(LocalMessageSource lms) {
        this.lms = lms;
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
        keyboard.add(routeButton(userData));
        if (Objects.isNull(userData.getTempStartDate()))
            keyboard.add(datesButton(userData));
        keyboard.add(deliveryButton(userData));
        keyboard.add(seatsButton(userData));
        keyboard.add(commentButton(userData));
        keyboard.add(confirmButton(userData.getRole()));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> routeButton(UserData userData) {
        var route = userData.getRoute();
        return route.isEmpty() ?
                List.of(buttons.get(ADD_ROUTE)) :
                List.of(buttons.get(EDIT_ROUTE));
    }

    private List<InlineKeyboardButton> datesButton(UserData userData) {
        return userData.getTempStartDate() == null ?
                List.of(buttons.get(ADD_DATES)) :
                List.of(buttons.get(EDIT_DATES));
    }

    private List<InlineKeyboardButton> deliveryButton(UserData userData) {
        return userData.getDeliverySize() == 0 ?
                List.of(buttons.get(ADD_DELIVERY)) :
                List.of(buttons.get(EDIT_DELIVERY));
    }

    private List<InlineKeyboardButton> seatsButton(UserData userData) {
        return userData.getSeats() == 0 ?
                List.of(buttons.get(ADD_SEATS)) :
                List.of(buttons.get(EDIT_SEATS));
    }

    private List<InlineKeyboardButton> commentButton(UserData userData) {
        return userData.getComment() == null ?
                List.of(buttons.get(ADD_COMMENT)) :
                List.of(buttons.get(EDIT_COMMENT));
    }

    private List<InlineKeyboardButton> confirmButton(Role role) {
        return switch (role) {
            case COURIER -> List.of(buttons.get(FORM_CONFIRM_AS_COURIER));
            case CLIENT -> List.of(buttons.get(FORM_CONFIRM_AS_CLIENT));
            case REGISTRATIONS_VIEWER -> List.of(buttons.get(FORM_CONFIRM_AS_REGISTRATION_VIEWER));
            case REQUESTS_VIEWER -> List.of(buttons.get(FORM_CONFIRM_AS_REQUEST_VIEWER));
        };
    }
}
