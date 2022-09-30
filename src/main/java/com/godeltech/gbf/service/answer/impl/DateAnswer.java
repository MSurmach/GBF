package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.godeltech.gbf.model.State.DATE_FROM;
import static com.godeltech.gbf.model.State.DATE_TO;
import static com.godeltech.gbf.service.answer.CommonAnswerCode.*;

@Service
public class DateAnswer implements Answer {

    public final static String COURIER_DATE_FROM_CODE = "courier.date_from";
    public final static String COURIER_DATE_TO_CODE = "courier.date_to";

    public final static String CUSTOMER_DATE_FROM_CODE = "customer.date_from";
    public final static String CUSTOMER_DATE_TO_CODE = "customer.date_to";
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
        String nowDateInfoCode = DATE_TODAY_CODE.getCode();
        State state = userData.getCurrentState();
        Role role = userData.getRole();
        String countryCityInfoCode = state == DATE_FROM ?
                COUNTRY_CITY_START_CODE.getCode() :
                COUNTRY_CITY_FINISH_CODE.getCode();
        String country = state == DATE_TO ?
                localMessageSource.getLocaleMessage(userData.getCountryTo()) :
                localMessageSource.getLocaleMessage(userData.getCountryFrom());
        String city = state == DATE_TO ?
                localMessageSource.getLocaleMessage(userData.getCityTo()) :
                localMessageSource.getLocaleMessage(userData.getCityFrom());
        String questionCode = switch (role) {
            case COURIER -> state == DATE_FROM ? COURIER_DATE_FROM_CODE : COURIER_DATE_TO_CODE;
            case CUSTOMER -> state == DATE_FROM ? CUSTOMER_DATE_FROM_CODE : CUSTOMER_DATE_TO_CODE;
            default -> null;
        };
        return localMessageSource.getLocaleMessage(nowDateInfoCode, nowDate) +
                localMessageSource.getLocaleMessage(countryCityInfoCode, country, city) +
                System.lineSeparator() +
                localMessageSource.getLocaleMessage(questionCode);
    }
}
