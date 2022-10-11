package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import org.springframework.stereotype.Component;

@Component
public class WrongInputMessage implements Message {
    private final static String WRONG_INPUT_CODE = "wrong_input";
    private final LocalMessageSource localMessageSource;

    public WrongInputMessage(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(WRONG_INPUT_CODE, userData.getUsername());
    }
}
