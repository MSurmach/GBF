package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DATE_TO_QUIZ;
import static com.godeltech.gbf.service.text.CommonAnswerCode.COUNTRY_CITY_FINISH_CODE;
import static com.godeltech.gbf.service.text.CommonAnswerCode.COUNTRY_CITY_START_CODE;

@Service
@AllArgsConstructor
public class DateQuizText implements Text {
    private final static String DATE_FROM_QUIZ_CODE = "customer.dateFrom.quiz";
    private final static String DATE_TO_QUIZ_CODE = "customer.dateTo.quiz";
    private LocalMessageSource localMessageSource;

    @Override
    public String getText(UserData userData) {
        State currentState = userData.getStateHistory().peek();
        String countryCityInfoCode;
        String country;
        String city;
        String quizCode;
        if (currentState == DATE_TO_QUIZ) {
            countryCityInfoCode = COUNTRY_CITY_FINISH_CODE.getCode();
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            city = localMessageSource.getLocaleMessage(userData.getCityTo());
            quizCode = DATE_TO_QUIZ_CODE;
        } else {
            countryCityInfoCode = COUNTRY_CITY_START_CODE.getCode();
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            city = localMessageSource.getLocaleMessage(userData.getCityFrom());
            quizCode = DATE_FROM_QUIZ_CODE;
        }
        return localMessageSource.getLocaleMessage(countryCityInfoCode, country, city) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(quizCode);
    }
}
