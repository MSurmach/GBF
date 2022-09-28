package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WrongInputAnswer implements Answer {
    private final static String WRONG_INPUT_CODE = "wrong_input";
    private final LocalMessageSource localMessageSource;

    public WrongInputAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(WRONG_INPUT_CODE, userData.getUsername());
    }
}
