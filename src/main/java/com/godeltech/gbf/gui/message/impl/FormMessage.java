package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.model.db.RoutePoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.utils.DateUtils.formatDate;

@Component
@AllArgsConstructor
public class FormMessage implements Message {
    public final static String COURIER_INSTRUCTION_CODE = "form.courier.instruction";
    public final static String COURIER_ESSENTIAL_INFO_CODE = "form.courier.essential.info";
    public final static String CLIENT_INSTRUCTION_CODE = "form.client.instruction";
    public final static String CLIENT_ESSENTIAL_INFO_CODE = "form.client.essential.info";
    public final static String DETAILS_HEADER_EMPTY = "form.details.header.empty";
    public final static String DETAILS_HEADER_FULL = "form.details.header.full";
    public final static String CARGO_DOCUMENTS_SELECTED = "cargo.documents.selected";
    public final static String CARGO_PACKAGE_SELECTED = "cargo.package.selected";
    public final static String CARGO_PEOPLE_SELECTED = "cargo.people.selected";
    public final static String DETAILS_INITIAL_POINT_HEADER = "form.details.initialPoint.header";
    public final static String DETAILS_FINAL_POINT_HEADER = "form.details.finalPoint.header";
    public final static String DETAILS_INTERMEDIATE_POINT_HEADER = "form.details.intermediatePoint.header";
    public final static String DETAILS_COUNTRY = "form.details.country";
    public final static String DETAILS_CITY = "form.details.city";
    public final static String DETAILS_VISIT_DATE = "form.details.visitDate";
    public final static String EMPTY = "";

    private LocalMessageSource lms;

    @Override
    public String initialMessage(UserData userData) {
        Role currentRole = userData.getRole();
        return switch (currentRole) {
            case COURIER -> lms.getLocaleMessage(COURIER_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(COURIER_ESSENTIAL_INFO_CODE);
            case CLIENT -> lms.getLocaleMessage(CLIENT_INSTRUCTION_CODE, userData.getUsername()) +
                    lms.getLocaleMessage(CLIENT_ESSENTIAL_INFO_CODE);
            case REGISTRATIONS_VIEWER -> null;
            case REQUESTS_VIEWER -> null;
        };
    }

    @Override
    public String getMessage(UserData userData) {
        return buildHeader(userData) +
                buildRoute(userData.getRoutePoints()) +
                buildCargoSummary(userData) +
                buildComment(userData);
    }

    private String buildHeader(UserData userData) {
        return userDataIsEmpty(userData) ?
                lms.getLocaleMessage(DETAILS_HEADER_EMPTY) :
                lms.getLocaleMessage(DETAILS_HEADER_FULL);
    }

    private String buildRoute(List<RoutePoint> route) {
        StringBuilder routeBuilder = new StringBuilder();
        route.forEach(
                routePoint -> {
                    String pointHeader = switch (routePoint.getStatus()) {
                        case INITIAL -> lms.getLocaleMessage(DETAILS_INITIAL_POINT_HEADER);
                        case INTERMEDIATE -> {
                            int index = route.indexOf(routePoint);
                            yield lms.getLocaleMessage(DETAILS_INTERMEDIATE_POINT_HEADER, String.valueOf(index));
                        }
                        case FINAL -> lms.getLocaleMessage(DETAILS_FINAL_POINT_HEADER);
                    };
                    routeBuilder.append(pointHeader);
                    Country country = routePoint.getCountry();
                    if (country != null)
                        routeBuilder.append(lms.getLocaleMessage(DETAILS_COUNTRY, country.getName()));
                    City city = routePoint.getCity();
                    if (city != null)
                        routeBuilder.append(lms.getLocaleMessage(DETAILS_CITY, city.getName()));
                    LocalDate visitDate = routePoint.getVisitDate();
                    if (visitDate != null)
                        routeBuilder.append(lms.getLocaleMessage(DETAILS_VISIT_DATE, formatDate(visitDate, lms.getLocale())));
                }
        );
        return routeBuilder.toString();
    }

    private String buildCargoSummary(UserData userData) {
        StringBuilder cargoSummary = new StringBuilder();
        if (userData.isDocuments())
            cargoSummary.append(lms.getLocaleMessage(CARGO_DOCUMENTS_SELECTED));
        if (userData.getPackageSize() != null)
            cargoSummary.append(lms.getLocaleMessage(
                    CARGO_PACKAGE_SELECTED,
                    userData.getPackageSize()));
        if (userData.getCompanionCount() != 0)
            cargoSummary.append(lms.getLocaleMessage(
                    CARGO_PEOPLE_SELECTED,
                    String.valueOf(userData.getCompanionCount())));
        return cargoSummary.toString();
    }

    private String buildComment(UserData userData) {
        return userData.getComment() != null ?
                userData.getComment() :
                EMPTY;
    }

    private boolean userDataIsEmpty(UserData userData) {
        return userData.getRoutePoints().isEmpty() &&
                Objects.isNull(userData.getComment()) &&
                Objects.isNull(userData.getPackageSize()) &&
                !userData.isDocuments() &&
                userData.getCompanionCount() == 0;
    }
}
