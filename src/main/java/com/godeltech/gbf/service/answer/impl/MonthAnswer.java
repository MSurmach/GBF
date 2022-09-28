package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

@Component
public class MonthAnswer implements Answer {
    private final static String MONTH_CODE = "month";
    private final LocalMessageSource localMessageSource;

    public MonthAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
