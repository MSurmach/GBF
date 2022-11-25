package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.exception.NotFoundStateTypeException;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;

public class NotFoundTextMessageType implements TextMessageType {
    @Override
    public State getState() {
        return null;
    }

    @Override
    public String getMessage(Session session) {
        throw new NotFoundStateTypeException(NotFoundTextMessageType.class, session.getTelegramUser());
    }
}
