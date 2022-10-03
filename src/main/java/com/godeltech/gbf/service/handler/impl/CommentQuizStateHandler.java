package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.CommentBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.COMMENT;
import static com.godeltech.gbf.model.State.CONFIRMATION;

@Service
public class CommentQuizStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallback();
        var clicked = CommentBotButton.valueOf(callback);
        return switch (clicked) {
            case COMMENT_YES -> COMMENT;
            case COMMENT_NO -> CONFIRMATION;
            default -> userData.getCurrentState();
        };
    }
}
