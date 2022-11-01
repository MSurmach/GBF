package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.exception.NotFoundStateTypeException;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.HandlerType;
import lombok.extern.slf4j.Slf4j;

public class NotFoundHandlerType implements HandlerType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public State handle(UserData userData) {
        throw new NotFoundStateTypeException(NotFoundHandlerType.class,userData.getUsername(),userData.getId());
    }
}
