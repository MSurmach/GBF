package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.RegistrationEditorBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static com.godeltech.gbf.management.button.CalendarBotButton.INIT;
import static com.godeltech.gbf.model.Role.COURIER;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RegistrationEditorStateHandler implements StateHandler {

    private UserService userService;

    @Override
    public State handle(UserData userData) {
        LinkedList<String> callbackHistory = userData.getCallbackHistory();
        String callback = callbackHistory.peek();
        userData.setRole(REGISTRATIONS_VIEWER);
        var clickedButton = RegistrationEditorBotButton.valueOf(callback);
        return switch (clickedButton) {
            case EDIT_COUNTRY_CITY_FROM -> COUNTRY_FROM;
            case EDIT_COUNTRY_CITY_TO -> COUNTRY_TO;
            case EDIT_DATE_FROM -> {
                callbackHistory.push(INIT + ":" + userData.getDateFrom());
                yield DATE_FROM;
            }
            case EDIT_DATE_TO -> {
                callbackHistory.push(INIT + ":" + userData.getDateTo());
                yield DATE_TO;
            }
            case EDIT_COMMENT -> {
                callbackHistory.push(userData.getComment());
                yield COMMENT_CONFIRM;
            }
            case EDIT_CARGO -> CARGO_MENU;
            case EDIT_CONFIRM -> {
                userData.setRole(COURIER);
                userService.save(userData);
                userData.setRole(REGISTRATIONS_VIEWER);
                yield REGISTRATIONS;
            }
        };
    }
}
