package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.RegistrationEditorBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.management.button.CalendarBotButton.INIT;
import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.*;

@Service
@AllArgsConstructor
public class RegistrationEditorStateHandler implements StateHandler {

    private UserDataRepository userDataRepository;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallback();
        userData.setRole(REGISTRATIONS_VIEWER);
        var clickedButton = RegistrationEditorBotButton.valueOf(callback);
        return switch (clickedButton) {
            case EDIT_COUNTRY_CITY_FROM -> COUNTRY_FROM;
            case EDIT_COUNTRY_CITY_TO -> COUNTRY_TO;
            case EDIT_DATE_FROM -> {
                userData.setCallback(INIT + ":" + userData.getDateFrom());
                yield DATE_FROM;
            }
            case EDIT_DATE_TO -> {
                userData.setCallback(INIT + ":" + userData.getDateTo());
                yield DATE_TO;
            }
            case EDIT_COMMENT -> {
                userData.setCallback(userData.getComment());
                yield COMMENT_CONFIRM;
            }
            case EDIT_CARGO -> CARGO_MENU;
            case EDIT_CONFIRM -> {
                userDataRepository.save(userData);
                yield REGISTRATIONS;
            }
        };
    }
}
