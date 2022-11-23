package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;


@Service
public class CommentHandlerType implements HandlerType {
    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public State handle(Session session) {
        String comment = session.getCallbackHistory().peek();
        session.setComment(comment);
        session.getStateHistory().pop();
        return session.getStateHistory().peek();
    }
}
