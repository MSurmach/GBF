package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import org.springframework.stereotype.Component;

@Component
public class SuccessText implements Text {
    private final static String SUCCESS_CODE = "registration.success";

    private final LocalMessageSource localMessageSource;

    public SuccessText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(SUCCESS_CODE);
    }
}
