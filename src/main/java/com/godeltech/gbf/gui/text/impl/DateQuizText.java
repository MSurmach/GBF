package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.text.CommonAnswerCode;
import com.godeltech.gbf.gui.text.Text;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.DATE_TO_QUIZ;

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
            countryCityInfoCode = CommonAnswerCode.COUNTRY_CITY_FINISH_CODE.getCode();
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            city = localMessageSource.getLocaleMessage(userData.getCityTo());
            quizCode = DATE_TO_QUIZ_CODE;
        } else {
            countryCityInfoCode = CommonAnswerCode.COUNTRY_CITY_START_CODE.getCode();
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            city = localMessageSource.getLocaleMessage(userData.getCityFrom());
            quizCode = DATE_FROM_QUIZ_CODE;
        }
        return localMessageSource.getLocaleMessage(countryCityInfoCode, country, city) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(quizCode);
    }
}
