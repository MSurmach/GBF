package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import org.springframework.stereotype.Component;

@Component
public class WrongInputText implements Text {
    private final static String WRONG_INPUT_CODE = "wrong_input";
    private final LocalMessageSource localMessageSource;

    public WrongInputText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(WRONG_INPUT_CODE, userData.getUsername());
    }
}