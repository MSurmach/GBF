package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.country.CountryService;
import com.godeltech.gbf.service.handler.Handler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.ROUTE_POINT_FORM;

@Service
@AllArgsConstructor
public class CountryHandler implements Handler {
    private CountryService countryService;

    @Override
    public State handle(UserData userData) {
        String callback = userData.getCallbackHistory().peek();
        Country country = countryService.findCountryByName(callback);
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        tempRoutePoint.setCountry(country);
        return ROUTE_POINT_FORM;
    }
}
