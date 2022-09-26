package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public String getAnswer(UserData userData, List<UserData>... users) {
        String nowDate = LocalDate.now().format(dateFormatter);
        State state = userData.getCurrentState();
        String neededCode;
        String country;
        String city;
        if (state == State.DATE_TO) {
            country = localMessageSource.getLocaleMessage(userData.getCountryTo());
            city = localMessageSource.getLocaleMessage(userData.getCityTo());
            neededCode = DATE_TO_CODE;
        } else {
            country = localMessageSource.getLocaleMessage(userData.getCountryFrom());
            city = localMessageSource.getLocaleMessage(userData.getCityFrom());
            neededCode = DATE_FROM_CODE;
        }
        return localMessageSource.getLocaleMessage(neededCode, country, city, nowDate);
    }
}
