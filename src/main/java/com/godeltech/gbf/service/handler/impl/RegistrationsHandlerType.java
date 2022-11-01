package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RegistrationBotButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RegistrationsHandlerType implements HandlerType {
    private TelegramUserService telegramUserService;

    @Override
    public State getState() {
        return REGISTRATIONS;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        long userId = Long.parseLong(splittedCallback[1]);
        sessionData.setPageNumber(0);
        return switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                TelegramUser telegramUser = getUserFromPage(sessionData.getPage());
                ModelUtils.copyData(sessionData, telegramUser);
                sessionData.setRole(Role.REGISTRATIONS_VIEWER);
                yield FORM;
            }
            case REGISTRATION_DELETE -> {
                //telegramUserService.deleteById(userId);
                yield REGISTRATIONS;
            }
            case REGISTRATION_FIND_CLIENTS -> {
                TelegramUser telegramUserFromPage = getUserFromPage(sessionData.getPage());
                sessionData.setTempForSearch(telegramUserFromPage);
                yield CLIENTS_LIST_RESULT;
            }
        };
    }

    private TelegramUser getUserFromPage(Page<TelegramUser> page) {
        return page.getContent().get(0);
    }
}
