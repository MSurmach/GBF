package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.RequestButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RequestsHandlerType implements HandlerType {

    private UserService userService;

    @Override
    public State getState() {
        return REQUESTS;
    }

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splitCallback = callback.split(":");
        RequestButton clickedButton = RequestButton.valueOf(splitCallback[0]);
        long userId = Long.parseLong(splitCallback[1]);
        userData.setPageNumber(0);
        return switch (clickedButton) {
            case REQUEST_EDIT -> {
                TelegramUser telegramUser = getUserFromPage(userData.getPage());
                ModelUtils.copyData(userData, telegramUser);
                userData.setRole(Role.REQUESTS_VIEWER);
                yield FORM;
            }
            case REQUEST_DELETE -> {
                userService.deleteById(userId);
                yield REQUESTS;
            }
            case REQUEST_FIND_COURIERS -> {
                TelegramUser telegramUserFromPage = getUserFromPage(userData.getPage());
                userData.setTempForSearch(telegramUserFromPage);
                yield COURIERS_LIST_RESULT;
            }
        };
    }

    private TelegramUser getUserFromPage(Page<TelegramUser> page) {
        return page.getContent().get(0);
    }
}
