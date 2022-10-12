package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.Handler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.COMMENT_CONFIRM;

@Service
public class CommentHandler implements Handler {
    @Override
    public State handle(UserData userData) {
        String comment = userData.getCallbackHistory().peek();
        userData.setComment(comment);
        userData.getStateHistory().pop();
        return userData.getStateHistory().peek();
    }
}
