package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class CommentStateHandler implements StateHandler {
    @Override
    public void handle(UserData userData) {
        String comment = userData.getCallback();
        userData.setComment(comment);
        userData.setCurrentState(State.COMMENT_CONFIRM);
    }
}
