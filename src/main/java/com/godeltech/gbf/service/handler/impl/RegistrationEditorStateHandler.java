package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.COMMENT_CONFIRM;

@Service
@AllArgsConstructor
public class RegistrationEditorStateHandler implements StateHandler {

    private UserDataRepository userDataRepository;

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        userData.setRole(REGISTRATIONS_VIEWER);
        BotButton.RegistrationEditor botButton = BotButton.RegistrationEditor.valueOf(callback);
        switch (botButton) {
            case EDIT_COUNTRY_CITY_FROM -> {
                userData.setCurrentState(State.COUNTRY_FROM);
            }
            case EDIT_COUNTRY_CITY_TO -> {
                userData.setCurrentState(State.COUNTRY_TO);
            }
            case EDIT_DATE_FROM -> {
                userData.setCurrentState(State.DATE_FROM);
                userData.setCallback(BotButton.Calendar.INIT + ":" + userData.getDateFrom());
            }
            case EDIT_DATE_TO -> {
                userData.setCurrentState(State.DATE_TO);
                userData.setCallback(BotButton.Calendar.INIT + ":" + userData.getDateTo());
            }
            case EDIT_COMMENT -> {
                userData.setCurrentState(COMMENT_CONFIRM);
                userData.setCallback(userData.getComment());
            }
            case EDIT_CARGO -> {
                userData.setCurrentState(State.CARGO_MENU);
            }
            case EDIT_CONFIRM -> {
                userDataRepository.save(userData);
                userData.setCurrentState(State.REGISTRATIONS);
            }
        }
    }
}
