package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class YearMessageType implements MessageType {
    private final static String YEAR_CODE = "year.question";
    private LocalMessageSource localMessageSource;

    public YearMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.YEAR;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(YEAR_CODE);
    }
}
