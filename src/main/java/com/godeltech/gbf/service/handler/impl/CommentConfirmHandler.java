package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CommentBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;
import static com.godeltech.gbf.model.State.*;

@Service
public class CommentConfirmHandler implements Handler {
    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        var clickedButton = CommentBotButton.valueOf(callback);
        return switch (clickedButton) {
            case COMMENT_CONFIRM -> {
                Role role = userData.getRole();
                yield role == REGISTRATIONS_VIEWER ? REGISTRATION_EDITOR : SUMMARY_DATA_TO_CONFIRM;
            }
            case COMMENT_EDIT -> COMMENT;
            default -> userData.getStateHistory().peek();
        };
    }
}
