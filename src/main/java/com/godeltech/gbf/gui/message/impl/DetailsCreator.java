package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.CargoSize;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailsCreator {
    public final static String DETAILS_INITIAL_POINT_HEADER_CODE = "details.initialPoint.header";
    public final static String DETAILS_FINAL_POINT_HEADER_CODE = "details.finalPoint.header";
    public final static String DETAILS_INTERMEDIATE_POINT_HEADER_CODE = "details.intermediatePoint.header";

    public final static String DETAILS_GEOPOINT_CODE = "details.geopoint";
    public final static String DETAILS_VISIT_DATE_CODE = "details.dates.visit";
    public final static String DETAILS_CARGO_DOCUMENTS_SELECTED_CODE = "details.cargo.documents.selected";
    public final static String DETAILS_CARGO_PACKAGE_SELECTED_CODE = "details.cargo.package.selected";
    public final static String DETAILS_CARGO_PEOPLE_SELECTED_CODE = "details.cargo.people.selected";
    public final static String DETAILS_COURIER_CARGO_CODE = "details.courier.cargo";
    public final static String DETAILS_CLIENT_CARGO_CODE = "details.client.cargo";
    public final static String DETAILS_COMMENT_CODE = "details.comment";
    public final static String EMPTY = "";

    private LocalMessageSource lms;

    String createAllDetails(UserData userData) {
        return createRouteDetails(userData.getTempRoute()) +
                createCargoDetails(userData) +
                createCommentDetails(userData.getComment());
    }

    String createRouteDetails(List<RoutePoint> route) {
        StringBuilder routeBuilder = new StringBuilder();
        route.forEach(
                routePoint -> {
                    String routePointSummary = createRoutePointDetails(routePoint);
                    routeBuilder.append(routePointSummary);
                }
        );
        return routeBuilder.toString();
    }

    String createRoutePointDetails(RoutePoint routePoint) {
//        if (routePoint.isEmpty()) return EMPTY;
//        StringBuilder routeBuilder = new StringBuilder();
//        City city = routePoint.getCity();
////        Country country = routePoint.getCountry();
////        LocalDate startDate = routePoint.getStartDate();
////        LocalDate endDate = routePoint.getEndDate();
//        String routePointHeader = switch (routePoint.getStatus()) {
//            case INITIAL -> lms.getLocaleMessage(DETAILS_INITIAL_POINT_HEADER_CODE);
//            case INTERMEDIATE -> {
//                String order = String.valueOf(routePoint.getOrderNumber());
//                yield lms.getLocaleMessage(DETAILS_INTERMEDIATE_POINT_HEADER_CODE,
//                        lms.getLocaleMessage(order));
//            }
//            case FINAL -> lms.getLocaleMessage(DETAILS_FINAL_POINT_HEADER_CODE);
//        };
//        routeBuilder.append(routePointHeader);
//        if (country != null) {
//            String geopoint;
//            String localCountry = lms.getLocaleMessage(country.getName());
//            if (city != null) {
//                String localCity = lms.getLocaleMessage(city.getName());
//                geopoint = lms.getLocaleMessage(DETAILS_GEOPOINT_CODE, localCountry, ", " + localCity);
//            }
//            else geopoint = lms.getLocaleMessage(DETAILS_GEOPOINT_CODE, localCountry, EMPTY);
//            routeBuilder.append(geopoint);
//        }
//        if (startDate != null) {
//            String formattedDate;
//            if (!startDate.equals(endDate)) {
//                formattedDate = dateAsRange(startDate, endDate);
//            } else {
//                formattedDate = shortFormatDate(startDate);
//            }
//            routeBuilder.append(lms.getLocaleMessage(DETAILS_VISIT_DATE_CODE, formattedDate));
//        }
//        return routeBuilder.toString();
    return null;
    }


    String createCargoDetails(UserData userData) {
        StringBuilder cargoSummary = new StringBuilder();
        String cargoHeader = switch (userData.getRole()) {
            case COURIER, REGISTRATIONS_VIEWER -> lms.getLocaleMessage(DETAILS_COURIER_CARGO_CODE);
            case CLIENT, REQUESTS_VIEWER -> lms.getLocaleMessage(DETAILS_CLIENT_CARGO_CODE);
        };
        if (userData.getDeliverySize() != 0)
            cargoSummary.append(lms.getLocaleMessage(
                    DETAILS_CARGO_PACKAGE_SELECTED_CODE,
                    CargoSize.getSizeName(userData.getDeliverySize())));
        if (userData.getSeats() != 0)
            cargoSummary.append(lms.getLocaleMessage(
                    DETAILS_CARGO_PEOPLE_SELECTED_CODE,
                    String.valueOf(userData.getSeats())));
        return cargoSummary.toString();
    }

    String createCommentDetails(String comment) {
        return comment != null ?
                lms.getLocaleMessage(DETAILS_COMMENT_CODE, comment) :
                EMPTY;
    }
}
