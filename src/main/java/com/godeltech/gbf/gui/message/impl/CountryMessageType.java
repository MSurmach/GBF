package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CountryMessageType implements MessageType {
    public final static String COUNTRY_QUESTION_CODE = "country.question";
    private final LocalMessageSource localMessageSource;

    @Override
    public State getState() {
        return State.COUNTRY;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(COUNTRY_QUESTION_CODE);
    }
}
