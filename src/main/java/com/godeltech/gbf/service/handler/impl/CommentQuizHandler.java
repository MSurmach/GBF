package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.gui.button.CommentBotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.COMMENT;
import static com.godeltech.gbf.model.State.SUMMARY_DATA_TO_CONFIRM;

@Service
public class CommentQuizHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        var clicked = CommentBotButton.valueOf(callback);
        return switch (clicked) {
            case COMMENT_YES -> COMMENT;
            case COMMENT_NO -> SUMMARY_DATA_TO_CONFIRM;
            default -> userData.getStateHistory().peek();
        };
    }
}
