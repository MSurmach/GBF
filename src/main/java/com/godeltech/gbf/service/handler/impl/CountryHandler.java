package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.service.handler.Handler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ROUTE_POINT_FORM;

@Service
@AllArgsConstructor
public class CountryHandler implements Handler {

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        String[] splittedCallback = callback.split(":");
        Integer countryId = Integer.valueOf(splittedCallback[1]);
        String countryName = splittedCallback[0];
        Country country = new Country(countryId, countryName);
        userData.getTempRoutePoint().setCountry(country);
        return ROUTE_POINT_FORM;
    }
}
