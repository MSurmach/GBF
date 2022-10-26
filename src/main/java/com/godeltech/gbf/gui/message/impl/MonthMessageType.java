package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class MonthMessageType implements MessageType {
    private final static String MONTH_CODE = "month.question";
    private final LocalMessageSource localMessageSource;

    public MonthMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.MONTH;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
