package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.godeltech.gbf.gui.message.impl.FormMessage.EMPTY_CODE;
import static com.godeltech.gbf.utils.DateUtils.formatDate;

@Component
@AllArgsConstructor
public class RoutePointFormMessage implements Message {
    private final LocalMessageSource lms;
    public final static String DETAILS_INITIAL_POINT_HEADER_CODE = "routePointForm.details.initialPoint.header";
    public final static String DETAILS_FINAL_POINT_HEADER_CODE = "routePointForm.details.finalPoint.header";
    public final static String DETAILS_INTERMEDIATE_POINT_HEADER_CODE = "routePointForm.details.intermediatePoint.header";
    public final static String DETAILS_COUNTRY_CODE = "routePointForm.details.country";
    public final static String DETAILS_CITY_CODE = "routePointForm.details.city";
    public final static String DETAILS_VISIT_DATE_CODE = "routePointForm.details.visitDate";
    public final static String COURIER_HEADER_CODE = "routePointForm.courier.header";
    public final static String CLIENT_HEADER_CODE = "routePointForm.client.header";

    @Override
    public String getMessage(UserData userData) {
        RoutePoint tempRoutePoint = userData.getTempRoutePoint();
        String header = switch (userData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(COURIER_HEADER_CODE, userData.getUsername());
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(CLIENT_HEADER_CODE, userData.getUsername());
        };
        return header + buildRoutePointSummary(tempRoutePoint);
    }

    String buildRoutePointSummary(RoutePoint routePoint) {
        if (routePoint.isEmpty()) return EMPTY_CODE;
        StringBuilder routeBuilder = new StringBuilder();
        Country country = routePoint.getCountry();
        City city = routePoint.getCity();
        LocalDate visitDate = routePoint.getVisitDate();
        String routePointHeader = switch (routePoint.getStatus()) {
            case INITIAL -> lms.getLocaleMessage(DETAILS_INITIAL_POINT_HEADER_CODE);
            case INTERMEDIATE -> {
                String order = String.valueOf(routePoint.getOrder());
                yield lms.getLocaleMessage(DETAILS_INTERMEDIATE_POINT_HEADER_CODE,
                        lms.getLocaleMessage(order));
            }
            case FINAL -> lms.getLocaleMessage(DETAILS_FINAL_POINT_HEADER_CODE);
        };
        routeBuilder.append(routePointHeader);
        if (country != null) {
            String localCountry = lms.getLocaleMessage(country.getName());
            routeBuilder.append(lms.getLocaleMessage(DETAILS_COUNTRY_CODE, localCountry));
        }
        if (city != null) {
            String localCity = lms.getLocaleMessage(city.getName());
            routeBuilder.append(lms.getLocaleMessage(DETAILS_CITY_CODE, localCity));
        }
        if (visitDate != null) {
            String formattedDate = formatDate(visitDate, lms.getLocale());
            routeBuilder.append(lms.getLocaleMessage(DETAILS_VISIT_DATE_CODE, formattedDate));
        }
        return routeBuilder.toString();
    }
}
