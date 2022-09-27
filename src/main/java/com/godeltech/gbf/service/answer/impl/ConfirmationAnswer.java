package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.godeltech.gbf.service.answer.CommonAnswerCode.*;
import static com.godeltech.gbf.service.answer.impl.DateAnswer.DATE_PATTERN;

@Service
public class ConfirmationAnswer implements Answer {
    public final static String CONFIRMATION_NOTIFICATION_CODE = "confirmation.notification";
    private final DateTimeFormatter dateFormatter;
    private final LocalMessageSource localMessageSource;

    private final CargoMenuAnswer cargoMenuAnswer;

    public ConfirmationAnswer(LocalMessageSource localMessageSource, CargoMenuAnswer cargoMenuAnswer) {
        this.localMessageSource = localMessageSource;
        this.cargoMenuAnswer = cargoMenuAnswer;
        dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(localMessageSource.getLocale());
    }

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        String lineSeparator = System.lineSeparator();
        return notificationText(userData) +
                lineSeparator + lineSeparator +
                confirmationDataText(userData);
    }

    private String notificationText(UserData userData) {
        return localMessageSource.getLocaleMessage(CONFIRMATION_NOTIFICATION_CODE, userData.getUsername());
    }

    String confirmationDataText(UserData userData) {
        String startCityCountry = localMessageSource.getLocaleMessage(
                COUNTRY_CITY_START_CODE.getCode(),
                localMessageSource.getLocaleMessage(userData.getCountryFrom()),
                localMessageSource.getLocaleMessage(userData.getCityFrom()));
        String startDate = localMessageSource.getLocaleMessage(DATE_START_CODE.getCode(),
                userData.getDateFrom().format(dateFormatter));
        String finishCityCountry = localMessageSource.getLocaleMessage(
                COUNTRY_CITY_FINISH_CODE.getCode(),
                localMessageSource.getLocaleMessage(userData.getCountryTo()),
                localMessageSource.getLocaleMessage(userData.getCityTo()));
        String finishDate = localMessageSource.getLocaleMessage(DATE_FINISH_CODE.getCode(),
                userData.getDateTo().format(dateFormatter));
        String cargo = localMessageSource.getLocaleMessage(
                CARGO_DATA_CODE.getCode(),
                cargoMenuAnswer.buildSelectedContent(userData));
        String comment = localMessageSource.getLocaleMessage(
                COMMENT_CONTENT_CODE.getCode(),
                userData.getComment());
        return startCityCountry +
                startDate +
                finishCityCountry +
                finishDate +
                cargo +
                comment;
    }
}
