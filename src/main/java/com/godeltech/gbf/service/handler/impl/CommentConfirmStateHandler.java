package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.CommentBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.*;

@Service
public class CommentConfirmStateHandler implements StateHandler {
    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallback();
        var clickedButton = CommentBotButton.valueOf(callback);
        return switch (clickedButton) {
            case COMMENT_CONFIRM -> {
                Role role = userData.getRole();
                yield role== REGISTRATIONS_VIEWER? REGISTRATION_EDITOR: CONFIRMATION;
            }
            case COMMENT_EDIT -> COMMENT;
            default -> userData.getCurrentState();
        };
    }
}
