package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import org.springframework.stereotype.Component;

@Component
public class SuccessMessageType implements MessageType {
    private final static String SUCCESS_CODE = "registration.success";

    private final LocalMessageSource localMessageSource;

    public SuccessMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.SUCCESS_REGISTRATION;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return localMessageSource.getLocaleMessage(SUCCESS_CODE);
    }
}
