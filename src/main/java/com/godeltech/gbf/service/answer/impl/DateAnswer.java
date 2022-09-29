package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.godeltech.gbf.service.answer.CommonAnswerCode.*;

@Component
public class DateAnswer implements Answer {

    public final static String DATE_FROM_CODE = "date_from";
    public final static String DATE_TO_CODE = "date_to";
    public final static String DATE_PATTERN = "dd MMMM yyyy";

    private final LocalMessageSource localMessageSource;
    @Getter
    private DateTimeFormatter dateFormatter;

    public DateAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
        dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(localMessageSource.getLocale());
    }

    @Override
    public String getAnswer(UserData userData) {
        String nowDate = LocalDate.now().format(dateFormatter);
        String nowDateCode = DATE_TODAY_CODE.getCode();
        State state = userData.getCurrentState();
        String countryCityInfoCode;
        String dateCode;
        String country;
        String city;
        if (state == State.DATE_TO) {
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            city = localMessageSource.getLocaleMessage(userData.getCityTo());
            dateCode = DATE_TO_CODE;
            countryCityInfoCode = COUNTRY_CITY_FINISH_CODE.getCode();
        } else {
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            city = localMessageSource.getLocaleMessage(userData.getCityFrom());
            dateCode = DATE_FROM_CODE;
            countryCityInfoCode = COUNTRY_CITY_START_CODE.getCode();
        }
        return localMessageSource.getLocaleMessage(nowDateCode, nowDate) +
                localMessageSource.getLocaleMessage(countryCityInfoCode, country, city) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(dateCode);
    }
}
