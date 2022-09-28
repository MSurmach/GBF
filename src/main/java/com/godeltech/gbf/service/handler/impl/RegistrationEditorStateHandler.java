package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEditorStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
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
            }
            case EDIT_DATE_TO -> {
                userData.setCurrentState(State.DATE_TO);
            }
            case EDIT_COMMENT -> {
                userData.setCurrentState(State.COMMENT_QUIZ);
            }
            case EDIT_CARGO -> {
                userData.setCurrentState(State.CARGO_MENU);
            }
            case EDIT_CONFIRM -> {
                userData.setCurrentState(State.REGISTRATIONS);
            }
        }
    }
}
