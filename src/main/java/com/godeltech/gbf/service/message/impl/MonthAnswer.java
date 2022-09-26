package com.godeltech.gbf.service.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.message.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonthAnswer implements Answer {
    private final static String MONTH_CODE = "month";
    private final LocalMessageSource localMessageSource;

    public MonthAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getBotMessage(UserData userData, List<UserData>... users) {
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
