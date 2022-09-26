package com.godeltech.gbf.service.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.message.Answer;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.godeltech.gbf.service.message.impl.DateAnswer.DATE_PATTERN;

@Component
public class ConfirmationAnswer implements Answer {
    public final static String CONFIRMATION_COURIER_CODE = "confirmation.courier";
    public final static String CONFIRMATION_CUSTOMER_CODE = "confirmation.customer";
    public final static String COURIER_DATA_CODE = "courier.data";
    public final static String CUSTOMER_DATA_CODE = "customer.data";
    private final DateTimeFormatter dateFormatter;
    private final LocalMessageSource localMessageSource;

    public ConfirmationAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
        dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(localMessageSource.getLocale());
    }

    @Override
    public String getBotMessage(UserData userData, List<UserData>... users) {
        String lineSeparator = System.lineSeparator();
        String answer = mainText(userData) +
                lineSeparator + lineSeparator +
                confirmationDataText(userData);
        return answer;
    }

    private String mainText(UserData userData) {
        String code = userData.getStateFlow() == StateFlow.COURIER ?
                CONFIRMATION_COURIER_CODE :
                CONFIRMATION_CUSTOMER_CODE;
        return localMessageSource.getLocaleMessage(code, userData.getUsername());
    }

    private String confirmationDataText(UserData userData) {
        String code = userData.getStateFlow() == StateFlow.COURIER ?
                COURIER_DATA_CODE :
                CUSTOMER_DATA_CODE;
        return localMessageSource.getLocaleMessage(code,
                localMessageSource.getLocaleMessage(userData.getCountryFrom()),
                localMessageSource.getLocaleMessage(userData.getCityFrom()),
                userData.getDateFrom().format(dateFormatter),
                localMessageSource.getLocaleMessage(userData.getCountryTo()),
                localMessageSource.getLocaleMessage(userData.getCityTo()),
                userData.getDateTo().format(dateFormatter));
    }
}
