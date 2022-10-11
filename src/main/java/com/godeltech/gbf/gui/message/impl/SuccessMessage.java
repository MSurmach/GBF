package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class SuccessMessage implements Message {
    private final static String SUCCESS_CODE = "registration.success";

    private final LocalMessageSource localMessageSource;

    public SuccessMessage(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(SUCCESS_CODE);
    }
}
