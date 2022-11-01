package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;


@Service
public class CommentHandlerType implements HandlerType {
    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public State handle(SessionData sessionData) {
        String comment = sessionData.getCallbackHistory().peek();
        sessionData.setComment(comment);
        sessionData.getStateHistory().pop();
        return sessionData.getStateHistory().peek();
    }
}
