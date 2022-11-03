package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.godeltech.gbf.gui.button.MainMenuButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;

@Component
@AllArgsConstructor
@Slf4j
public class MainMenuKeyboardType implements KeyboardType {
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.MENU;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        log.debug("Create main menu keyboard type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        var courierButton = createLocalButton(COURIER, lms);
        var clientButton = createLocalButton(CLIENT, lms);
        var registrationsButton = createLocalButton(REGISTRATIONS_VIEWER, lms);
        var requestsButton = createLocalButton(REQUESTS_VIEWER, lms);
        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(courierButton),
                List.of(clientButton),
                List.of(registrationsButton, requestsButton));
        return new InlineKeyboardMarkup(keyboard);
    }
}
