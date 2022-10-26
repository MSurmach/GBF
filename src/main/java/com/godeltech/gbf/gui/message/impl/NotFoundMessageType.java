package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;

public class NotFoundMessageType implements MessageType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public String getMessage(UserData userData) {
        return null;
    }
}