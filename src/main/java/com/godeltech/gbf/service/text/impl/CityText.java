package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.model.State.CITY_TO;
import static com.godeltech.gbf.service.text.CommonAnswerCode.COUNTRY_SELECTED_CODE;

@Component
public class CityText implements Text {
    public final static String COURIER_CITY_FROM_CODE = "courier.city_from";
    public final static String COURIER_CITY_TO_CODE = "courier.city_to";

    public final static String CUSTOMER_CITY_FROM_CODE = "customer.city_from";
    public final static String CUSTOMER_CITY_TO_CODE = "customer.city_to";
    private final LocalMessageSource localMessageSource;

    public CityText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        Role role = userData.getRole();
        State state = userData.getStateHistory().peek();
        String selectedCountryInfoCode = COUNTRY_SELECTED_CODE.getCode();
        String country = state == CITY_TO ?
                localMessageSource.getLocaleMessage(userData.getCountryTo()) :
                localMessageSource.getLocaleMessage(userData.getCountryFrom());
        String questionCode = switch (role) {
            case COURIER, REGISTRATIONS_VIEWER ->
                    state == State.CITY_FROM ? COURIER_CITY_FROM_CODE : COURIER_CITY_TO_CODE;
            case CLIENT, REQUESTS_VIEWER -> state == State.CITY_FROM ? CUSTOMER_CITY_FROM_CODE : CUSTOMER_CITY_TO_CODE;
        };
        return localMessageSource.getLocaleMessage(questionCode) +
                localMessageSource.getLocaleMessage(selectedCountryInfoCode, country);

    }
}
