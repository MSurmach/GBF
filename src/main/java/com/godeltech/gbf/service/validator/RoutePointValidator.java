package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RoutePointValidator {
    private LocalMessageSource lms;

    public final static String ALERT_COUNTRY_NOT_FOUND_CODE = "alert.country.notFound";
    public final static String ALERT_CITY_NOT_FOUND_CODE = "alert.city.notFound";
    public final static String ALERT_DATE_NOT_FOUND_CODE = "alert.date.notFound";

    public void checkAllNecessaryData(RoutePoint routePoint, Role role, String callbackQueryId) {
//        Country country = routePoint.getCountry();
        City city = routePoint.getCity();
//        LocalDate startDate = routePoint.getStartDate();
//        switch (role) {
//            case COURIER, REGISTRATIONS_VIEWER -> checkCountryIsNull(country, callbackQueryId).
//                    checkCityIsNull(city, callbackQueryId).
//                    checkDates(startDate, callbackQueryId);
//            case CLIENT, REQUESTS_VIEWER -> checkCountryIsNull(country, callbackQueryId);
//        }
    }

    public RoutePointValidator checkCountryIsNull(Country country, String callbackQueryId) {
        String alertMessage = lms.getLocaleMessage(ALERT_COUNTRY_NOT_FOUND_CODE);
        if (country == null)
            throw new GbfException(callbackQueryId, alertMessage);
        return this;
    }

    public RoutePointValidator checkCityIsNull(City city, String callbackQueryId) {
        String alertMessage = lms.getLocaleMessage(ALERT_CITY_NOT_FOUND_CODE);
        if (city == null)
            throw new GbfException(callbackQueryId, alertMessage);
        return this;
    }

    public RoutePointValidator checkDates(LocalDate startDate, String callbackQueryId) {
        String alertMessage = lms.getLocaleMessage(ALERT_DATE_NOT_FOUND_CODE);
        if (startDate == null)
            throw new GbfException(callbackQueryId, alertMessage);
        return this;
    }
}

