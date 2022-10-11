package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CargoPeopleMessage implements Message {

    private final static String CARGO_PEOPLE_CODE = "cargo.people";
    private final LocalMessageSource localMessageSource;

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PEOPLE_CODE);
    }
}
