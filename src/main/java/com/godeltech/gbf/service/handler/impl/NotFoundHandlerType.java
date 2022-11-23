package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.NotFoundStateTypeException;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;

public class NotFoundHandlerType implements HandlerType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public State handle(Session session) {
        throw new NotFoundStateTypeException(NotFoundHandlerType.class, session.getTelegramUser());
    }
}
