package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.godeltech.gbf.service.answer.CommonAnswerCode.COUNTRY_FINISH_CODE;
import static com.godeltech.gbf.service.answer.CommonAnswerCode.COUNTRY_START_CODE;

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
        String cityCode;
        String countryCode;
        if (state == State.CITY_TO) {
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            cityCode = CITY_TO_CODE;
            countryCode = COUNTRY_FINISH_CODE.getCode();
        } else {
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            cityCode = CITY_FROM_CODE;
            countryCode = COUNTRY_START_CODE.getCode();
        }
        return localMessageSource.getLocaleMessage(countryCode, country) + System.lineSeparator() + localMessageSource.getLocaleMessage(cityCode);
    }
}
