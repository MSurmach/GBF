package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RegistrationBotButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RegistrationsHandler implements Handler {
    private UserService userService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        var clickedButton = RegistrationBotButton.valueOf(splittedCallback[0]);
        long userId = Long.parseLong(splittedCallback[1]);
        userData.setPageNumber(0);
        return switch (clickedButton) {
            case REGISTRATION_EDIT -> {
                TelegramUser telegramUser = getUserFromPage(userData.getPage());
                ModelUtils.copyData(userData, telegramUser);
                userData.setRole(Role.REGISTRATIONS_VIEWER);
                yield FORM;
            }
            case REGISTRATION_DELETE -> {
                userService.deleteById(userId);
                yield REGISTRATIONS;
            }
            case REGISTRATION_FIND_CLIENTS -> {
                //UserRecord record = getRecordFromPage(userData, userId);
                //userData.setTempForSearch(new UserData(record));
                yield CLIENTS_LIST_RESULT;
            }
        };
    }

    private TelegramUser getUserFromPage(Page<TelegramUser> page) {
        return page.getContent().get(0);
    }
}
