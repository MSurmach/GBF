package com.godeltech.gbf.gui.keyboard.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.model.SessionData;
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
    private LocalMessageSource lms;

    @Override
    public State getState() {
        return State.MENU;
    }

    @Override
    public InlineKeyboardMarkup getKeyboardMarkup(SessionData sessionData) {
        log.debug("Create main menu keyboard type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(), sessionData.getUsername());
        var courierButton = createLocalButton(START_AS_COURIER, lms);
        var clientButton = createLocalButton(START_AS_CLIENT, lms);
        var myRegistrationsButton = createLocalButton(LOOK_AT_MY_REGISTRATIONS, lms);
        var myRequestsButton = createLocalButton(LOOK_AT_MY_REQUESTS, lms);
        var lookAtAllRegistrationsButton = createLocalButton(LOOK_AT_ALL_REGISTRATIONS, lms);
        var lookAtAllRequestsButton = createLocalButton(LOOK_AT_ALL_REQUESTS, lms);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(List.of(courierButton));
        keyboard.add(List.of(clientButton));
        keyboard.add(List.of(myRegistrationsButton, myRequestsButton));
        keyboard.add(List.of(lookAtAllRegistrationsButton, lookAtAllRequestsButton));
        keyboard.add(feedBackButtonsRow(sessionData.getTelegramUserId()));
        return new InlineKeyboardMarkup(keyboard);
    }

    private List<InlineKeyboardButton> feedBackButtonsRow(Long telegramUserId) {
        var sendFeedbackButton = createLocalButton(SEND_FEEDBACK, lms);
        var lookAtFeedbacksButton = createLocalButton(LOOK_AT_FEEDBACKS, lms);
        return adminIdList.contains(telegramUserId) ?
                List.of(sendFeedbackButton, lookAtFeedbacksButton) :
                List.of(sendFeedbackButton);
    }
}
