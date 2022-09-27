package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryAnswer implements Answer {
    public final static String COUNTRY_FROM_CODE = "country_from";
    public final static String COUNTRY_TO_CODE = "country_to";
    private LocalMessageSource localMessageSource;

    public CountryAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        State state = userData.getCurrentState();
        String neededCode = state == State.COUNTRY_FROM ? COUNTRY_FROM_CODE : COUNTRY_TO_CODE;
        return localMessageSource.getLocaleMessage(neededCode);
    }
}