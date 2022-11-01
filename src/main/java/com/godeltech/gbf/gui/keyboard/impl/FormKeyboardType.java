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

import static com.godeltech.gbf.gui.button.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.button.FormButton.*;
import static com.godeltech.gbf.gui.keyboard.KeyboardUtils.backAndMenuMarkup;

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
        boolean isRouteEmpty = userData.getRoute().isEmpty();
        keyboard.add(routeButton(isRouteEmpty));
        if (!isRouteEmpty)
            keyboard.add(datesButton(Objects.isNull(userData.getStartDate())));
        keyboard.add(deliveryButton(userData.getDeliverySize() == 0));
        keyboard.add(seatsButton(userData.getSeats() == 0));
        keyboard.add(commentButton(Objects.isNull(userData.getComment())));
        keyboard.add(confirmButton(userData.getRole()));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> routeButton(boolean isEmptyRoute) {
        return isEmptyRoute ?
                List.of(buttons.get(ADD_ROUTE)) :
                List.of(buttons.get(EDIT_ROUTE));
    }

    private List<InlineKeyboardButton> datesButton(boolean isStartDateNull) {
        return isStartDateNull ?
                List.of(buttons.get(ADD_DATES)) :
                List.of(buttons.get(EDIT_DATES));
    }

    private List<InlineKeyboardButton> deliveryButton(boolean isDeliverySizeZero) {
        return isDeliverySizeZero ?
                List.of(buttons.get(ADD_DELIVERY)) :
                List.of(buttons.get(EDIT_DELIVERY));
    }

    private List<InlineKeyboardButton> seatsButton(boolean isSeatsZero) {
        return isSeatsZero ?
                List.of(buttons.get(ADD_SEATS)) :
                List.of(buttons.get(EDIT_SEATS));
    }

    private List<InlineKeyboardButton> commentButton(boolean isCommentNull) {
        return isCommentNull ?
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
