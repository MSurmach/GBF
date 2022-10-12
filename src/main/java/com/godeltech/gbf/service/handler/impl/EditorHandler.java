package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.EditorBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static com.godeltech.gbf.gui.button.CalendarBotButton.INIT;
import static com.godeltech.gbf.model.Role.*;
import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class EditorHandler implements Handler {

    private UserService userService;

    @Override
    public State handle(UserData userData) {
        LinkedList<String> callbackHistory = userData.getCallbackHistory();
        String callback = callbackHistory.peek();
        if (userData.getRole() == COURIER)
            userData.setRole(REGISTRATIONS_VIEWER);
        if (userData.getRole() == CLIENT)
            userData.setRole(REQUESTS_VIEWER);
        var clickedButton = EditorBotButton.valueOf(callback);
        return switch (clickedButton) {
            case EDIT_COUNTRY_CITY_FROM -> COUNTRY;
            case EDIT_COUNTRY_CITY_TO -> COUNTRY;
            case EDIT_DATE_FROM, ADD_DATE_FROM -> {
                callbackHistory.push(INIT + ":" + userData.getDateFrom());
                yield DATE;
            }
            case DELETE_DATE_FROM -> {
                userData.setDateFrom(null);
                yield userData.getStateHistory().peek();
            }
            case EDIT_DATE_TO, ADD_DATE_TO -> {
                callbackHistory.push(INIT + ":" + userData.getDateTo());
                yield DATE;
            }
            case DELETE_DATE_TO -> {
                userData.setDateTo(null);
                yield userData.getStateHistory().peek();
            }
            case EDIT_COMMENT -> {
                callbackHistory.push(userData.getComment());
                yield COMMENT_CONFIRM;
            }
            case ADD_COMMENT -> COMMENT;
            case DELETE_COMMENT -> {
                userData.setComment(null);
                yield userData.getStateHistory().peek();
            }
            case EDIT_CARGO -> CARGO_MENU;
            case EDIT_CONFIRM -> {
                Role currentRole = userData.getRole();
                if (currentRole == REGISTRATIONS_VIEWER) {
                    userData.setRole(COURIER);
                    userService.save(userData);
                    userData.setRole(REGISTRATIONS_VIEWER);
                    yield REGISTRATIONS;
                } else {
                    userData.setRole(CLIENT);
                    userService.save(userData);
                    userData.setRole(REQUESTS_VIEWER);
                    yield REQUESTS;
                }
            }
        };
    }
}
