package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

@Component
public class YearAnswer implements Answer {
    private final static String YEAR_CODE = "year";
    private LocalMessageSource localMessageSource;

    public YearAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(YEAR_CODE);
    }
}
