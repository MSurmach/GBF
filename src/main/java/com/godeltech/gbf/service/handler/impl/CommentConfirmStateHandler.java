package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.CONFIRMATION;
import static com.godeltech.gbf.model.State.REGISTRATION_EDITOR;

@Service
public class CommentConfirmStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        BotButton.Comment clicked = BotButton.Comment.valueOf(callback);
        switch (clicked) {
            case COMMENT_CONFIRM -> {
                Role role = userData.getRole();
                State nextState = role== REGISTRATIONS_VIEWER? REGISTRATION_EDITOR: CONFIRMATION;
                userData.setCurrentState(nextState);
            }
            case COMMENT_EDIT -> {
                userData.setCurrentState(State.COMMENT);
            }
        }
    }
}
