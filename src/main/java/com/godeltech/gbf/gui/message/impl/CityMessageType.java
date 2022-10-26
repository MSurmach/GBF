package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CityMessageType implements MessageType {
    public final static String CITY_QUESTION_CODE = "city.question";
    private final LocalMessageSource localMessageSource;

    @Override
    public State getState() {
        return State.CITY;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CITY_QUESTION_CODE);
    }
}
