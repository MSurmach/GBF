package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.godeltech.gbf.gui.message.impl.FormMessage.EMPTY_CODE;
import static com.godeltech.gbf.utils.DateUtils.fullFormatDate;

@Component
@AllArgsConstructor
public class MessageUtil {
    public final static String DETAILS_INITIAL_POINT_HEADER_CODE = "routePointForm.details.initialPoint.header";
    public final static String DETAILS_FINAL_POINT_HEADER_CODE = "routePointForm.details.finalPoint.header";
    public final static String DETAILS_INTERMEDIATE_POINT_HEADER_CODE = "routePointForm.details.intermediatePoint.header";
    public final static String DETAILS_COUNTRY_CODE = "routePointForm.details.country";
    public final static String DETAILS_CITY_CODE = "routePointForm.details.city";
    public final static String DETAILS_VISIT_DATE_CODE = "routePointForm.details.visitDate";
    private LocalMessageSource lms;

    String buildRoute(List<RoutePoint> route) {
        StringBuilder routeBuilder = new StringBuilder();
        route.forEach(
                routePoint -> {
                    String routePointSummary = buildRoutePointSummary(routePoint);
                    routeBuilder.append(routePointSummary);
                }
        );
        return routeBuilder.toString();
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
                String order = String.valueOf(routePoint.getOrderNumber());
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
            String formattedDate = fullFormatDate(visitDate, lms.getLocale());
            routeBuilder.append(lms.getLocaleMessage(DETAILS_VISIT_DATE_CODE, formattedDate));
        }
        return routeBuilder.toString();
    }
}
