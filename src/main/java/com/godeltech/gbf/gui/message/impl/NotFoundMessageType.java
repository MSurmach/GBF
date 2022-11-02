package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.exception.NotFoundStateTypeException;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;

public class NotFoundMessageType implements MessageType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        throw new NotFoundStateTypeException(NotFoundMessageType.class, sessionData.getUsername(), sessionData.getOfferId());
    }
}
