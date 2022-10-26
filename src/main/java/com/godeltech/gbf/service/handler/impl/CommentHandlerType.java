package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;


@Service
public class CommentHandlerType implements HandlerType {
    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public State handle(UserData userData) {
        String comment = userData.getCallbackHistory().peek();
        userData.setComment(comment);
        userData.getStateHistory().pop();
        return userData.getStateHistory().peek();
    }
}
