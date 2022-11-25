package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.gui.button.MenuButton.*;
import static com.godeltech.gbf.gui.utils.ButtonUtils.createLocalButton;

@Component
@AllArgsConstructor
@Slf4j
public class MenuKeyboardType implements KeyboardType {
    @Value("#{'${adminIdList}'.split(',')}")
    private List<Long> adminIdList;
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.MENU;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(Session session) {
        log.debug("Create main menu keyboard type for session data with user: {}",
                session.getTelegramUser());
        LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
        var courierButton = createLocalButton(START_AS_COURIER, lms);
        var clientButton = createLocalButton(START_AS_CLIENT, lms);
        var myRegistrationsButton = createLocalButton(LOOK_AT_MY_REGISTRATIONS, lms);
        var myRequestsButton = createLocalButton(LOOK_AT_MY_REQUESTS, lms);
        var lookAtAllRegistrationsButton = createLocalButton(LOOK_AT_ALL_REGISTRATIONS, lms);
        var lookAtAllRequestsButton = createLocalButton(LOOK_AT_ALL_REQUESTS, lms);
        var sendFeedbackButton = createLocalButton(SEND_FEEDBACK, lms);
        var switchLanguage = createLocalButton(SWITCH_LANGUAGE, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(courierButton));
        keyboard.add(List.of(clientButton));
        keyboard.add(List.of(myRegistrationsButton, myRequestsButton));
        keyboard.add(List.of(lookAtAllRegistrationsButton, lookAtAllRequestsButton));
        keyboard.add(List.of(sendFeedbackButton, switchLanguage));
        if (adminIdList.contains(session.getTelegramUser().getId())){
            var adminPanelButton = createLocalButton(ADMIN_PANEL, lms);
            keyboard.add(List.of(adminPanelButton));
        }
        return new InlineKeyboardMarkup(keyboard);
    }
}
