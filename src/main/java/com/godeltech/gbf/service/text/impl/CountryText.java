package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryText implements Text {
    public final static String COURIER_COUNTRY_FROM_CODE = "courier.country_from";
    public final static String COURIER_COUNTRY_TO_CODE = "courier.country_to";
    public final static String CUSTOMER_COUNTRY_FROM_CODE = "customer.country_from";
    public final static String CUSTOMER_COUNTRY_TO_CODE = "customer.country_to";
    private LocalMessageSource localMessageSource;

    @Override
    public String getText(UserData userData) {
        Role role = userData.getRole();
        State state = userData.getStateHistory().peek();
        String neededCode = switch (role) {
            case COURIER, REGISTRATIONS_VIEWER ->
                    state == State.COUNTRY_FROM ? COURIER_COUNTRY_FROM_CODE : COURIER_COUNTRY_TO_CODE;
            case CLIENT, REQUESTS_VIEWER ->
                    state == State.COUNTRY_FROM ? CUSTOMER_COUNTRY_FROM_CODE : CUSTOMER_COUNTRY_TO_CODE;
        };
        return localMessageSource.getLocaleMessage(neededCode);
    }
}