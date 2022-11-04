package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.utils.MessageUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.gui.button.FormButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createButton;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;
import static com.godeltech.gbf.gui.utils.KeyboardUtils.backAndMenuMarkup;
import static com.godeltech.gbf.model.Role.COURIER;

@Component
@Slf4j
@AllArgsConstructor
public class FormKeyboardType implements KeyboardType {

    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FORM;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        log.debug("Create form keyboard type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(), sessionData.getUsername());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        boolean isRouteEmpty = sessionData.getRoute().isEmpty();
        keyboard.add(routeButton(sessionData.getRoute()));
        if (!isRouteEmpty)
            keyboard.add(datesButton(sessionData.getStartDate(), sessionData.getEndDate()));
        keyboard.add(deliveryButton(sessionData.getDelivery()));
        keyboard.add(seatsButton(sessionData.getSeats()));
        keyboard.add(commentButton(sessionData.getComment()));
        keyboard.add(
                sessionData.isEditable() ?
                        saveChangesButton() :
                        confirmButton(sessionData.getRole())
        );
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboard);
        return new KeyboardMarkupAppender(keyboardMarkup).
                append(backAndMenuMarkup(lms)).
                result();
    }

    private List<InlineKeyboardButton> routeButton(List<RoutePoint> route) {
        if (route.isEmpty()) return List.of(createLocalButton(ADD_ROUTE, lms));
        String routeContent = MessageUtils.routeContent(route, false, lms);
        String label = lms.getLocaleMessage(EDIT_ROUTE.name(), routeContent);
        return List.of(createButton(label, EDIT_ROUTE));
    }

    private List<InlineKeyboardButton> datesButton(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate)) return List.of(createLocalButton(ADD_DATES, lms));
        String datesContent = MessageUtils.datesContent(startDate, endDate);
        String label = lms.getLocaleMessage(EDIT_DATES.name(), datesContent);
        return List.of(createButton(label, EDIT_DATES));
    }

    private List<InlineKeyboardButton> deliveryButton(Delivery delivery) {
        if (Objects.isNull(delivery) || Objects.equals(delivery, Delivery.EMPTY))
            return List.of(createLocalButton(ADD_DELIVERY, lms));
        String label = lms.getLocaleMessage(EDIT_DELIVERY.name(), MessageUtils.deliveryContent(delivery, lms));
        return List.of(createButton(label, EDIT_DELIVERY));
    }

    private List<InlineKeyboardButton> seatsButton(Integer seats) {
        if (Objects.equals(seats, 0)) return List.of(createLocalButton(ADD_SEATS, lms));
        String label = lms.getLocaleMessage(EDIT_SEATS.name(), seats.toString());
        return List.of(createButton(label, EDIT_SEATS));
    }

    private List<InlineKeyboardButton> commentButton(String comment) {
        return Objects.isNull(comment) ?
                List.of(createLocalButton(ADD_COMMENT, lms)) :
                List.of(createLocalButton(EDIT_COMMENT, lms));
    }

    private List<InlineKeyboardButton> confirmButton(Role role) {
        return Objects.equals(role, COURIER) ?
                List.of(createLocalButton(REGISTER, lms)) :
                List.of(createLocalButton(SEARCH_CLIENTS, lms));
    }

    private List<InlineKeyboardButton> saveChangesButton() {
        return List.of(createLocalButton(SAVE_CHANGES, lms));
    }
}
