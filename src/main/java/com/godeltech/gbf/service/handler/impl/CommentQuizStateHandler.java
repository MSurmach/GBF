package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class CommentQuizStateHandler implements StateHandler {

    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        BotButton.Comment clicked = BotButton.Comment.valueOf(callback);
        switch (clicked) {

            case COMMENT_YES -> {
                userData.setCurrentState(State.COMMENT);
            }
            case COMMENT_NO -> {
                userData.setCurrentState(State.CONFIRMATION);
            }
        }
    }
}
