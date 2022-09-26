package com.godeltech.gbf.service.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.message.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuccessAnswer implements Answer {
    private final static String SUCCESS_CODE = "registration.success";

    private final LocalMessageSource localMessageSource;

    public SuccessAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getBotMessage(UserData userData, List<UserData>... users) {
        return localMessageSource.getLocaleMessage(SUCCESS_CODE);
    }
}
