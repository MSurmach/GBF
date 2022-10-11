package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.model.State.CITY_TO;
import static com.godeltech.gbf.gui.message.CommonMessageCode.COUNTRY_SELECTED_CODE;

@Component
@AllArgsConstructor
public class CityMessage implements Message {
    public final static String COURIER_CITY_FROM_CODE = "courier.city_from";
    public final static String COURIER_CITY_TO_CODE = "courier.city_to";

    public final static String CUSTOMER_CITY_FROM_CODE = "customer.city_from";
    public final static String CUSTOMER_CITY_TO_CODE = "customer.city_to";
    private final LocalMessageSource localMessageSource;

    @Override
    public String getMessage(UserData userData) {
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
