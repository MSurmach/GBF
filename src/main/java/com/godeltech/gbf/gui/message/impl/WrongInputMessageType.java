package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.gui.message.MessageType;
import org.springframework.stereotype.Component;

@Component
public class WrongInputMessageType implements MessageType {
    private final static String WRONG_INPUT_CODE = "wrong_input";
    private final LocalMessageSource localMessageSource;

    public WrongInputMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.WRONG_INPUT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return localMessageSource.getLocaleMessage(WRONG_INPUT_CODE, sessionData.getUsername());
    }
}
