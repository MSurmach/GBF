package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityAnswer implements Answer {
    public final static String CITY_FROM_CODE = "city_from";
    public final static String CITY_TO_CODE = "city_to";
    private final LocalMessageSource localMessageSource;

    public CityAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        State state = userData.getCurrentState();
        String country;
        String neededCode;
        if (state == State.CITY_TO) {
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            neededCode = CITY_TO_CODE;
        } else {
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            neededCode = CITY_FROM_CODE;
        }
        return localMessageSource.getLocaleMessage(neededCode, country);
    }
}
