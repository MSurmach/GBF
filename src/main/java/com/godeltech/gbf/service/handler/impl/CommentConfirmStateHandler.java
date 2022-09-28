package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class CommentConfirmStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String callback = userData.getCallback();
        BotButton.Comment clicked = BotButton.Comment.valueOf(callback);
        switch (clicked) {

            case COMMENT_CONFIRM -> {
                State nextState = State.CONFIRMATION;
                userData.setCurrentState(nextState);
            }
            case COMMENT_EDIT -> {
                userData.setCurrentState(State.COMMENT);
            }
        }
    }
}
