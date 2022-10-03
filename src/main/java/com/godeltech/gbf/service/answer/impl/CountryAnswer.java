package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

@Component
public class CountryAnswer implements Answer {
    public final static String COURIER_COUNTRY_FROM_CODE = "courier.country_from";
    public final static String COURIER_COUNTRY_TO_CODE = "courier.country_to";
    public final static String CUSTOMER_COUNTRY_FROM_CODE = "customer.country_from";
    public final static String CUSTOMER_COUNTRY_TO_CODE = "customer.country_to";
    private LocalMessageSource localMessageSource;

    public CountryAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData) {
        Role role = userData.getRole();
        State state = userData.getStateHistory().peek();
        String neededCode = switch (role) {
            case COURIER -> state == State.COUNTRY_FROM ? COURIER_COUNTRY_FROM_CODE : COURIER_COUNTRY_TO_CODE;
            case CUSTOMER -> state == State.COUNTRY_FROM ? CUSTOMER_COUNTRY_FROM_CODE : CUSTOMER_COUNTRY_TO_CODE;
            default -> null;
        };
        return localMessageSource.getLocaleMessage(neededCode);
    }
}
