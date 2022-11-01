package com.godeltech.gbf.gui.message;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.DateUtils;
import com.godeltech.gbf.model.db.RoutePoint;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MessageUtils {
    public final static String DETAILS_DATES_CODE = "details.dates";
    public final static String DETAILS_COMMENT_CODE = "details.comment";
    public final static String DETAILS_ROUTE_CODE = "details.route";
    public final static String DETAILS_SEATS_CODE = "details.seats";
    public final static String DETAILS_EMPTY = "";

    public static String routeDetails(List<RoutePoint> route, LocalMessageSource lms) {
        if (route.isEmpty()) return DETAILS_EMPTY;
        final String arrow = " ➜ ";
        var stringBuilder = new StringBuilder();
        for (int index = 0; index < route.size(); index++) {
            RoutePoint routePoint = route.get(index);
            String localCityName = lms.getLocaleMessage(routePoint.getCity().getName());
            String cityStatusImage = statusImage(routePoint, lms);
            stringBuilder.append(cityStatusImage).append(" ").append(localCityName);
            if (index != route.size() - 1)
                stringBuilder.append(arrow);
        }
        return lms.getLocaleMessage(DETAILS_ROUTE_CODE, stringBuilder.toString());
    }

    public static String statusImage(RoutePoint routePoint, LocalMessageSource lms) {
        return switch (routePoint.getStatus()) {
            case INITIAL -> "\uD83D\uDEA9";
            case INTERMEDIATE -> lms.getLocaleMessage(String.valueOf(routePoint.getOrderNumber()));
            case FINAL -> "\uD83C\uDFC1";
        };
    }

    public static String commentDetails(String comment, LocalMessageSource lms) {
        return comment != null ?
                lms.getLocaleMessage(DETAILS_COMMENT_CODE, comment) :
                DETAILS_EMPTY;
    }

    public static String datesDetails(LocalDate startDate, LocalDate endDate, LocalMessageSource lms) {
        if (Objects.isNull(startDate) && Objects.isNull(endDate)) return DETAILS_EMPTY;
        return startDate.equals(endDate) ?
                lms.getLocaleMessage(DETAILS_DATES_CODE, DateUtils.shortFormatDate(startDate)) :
                lms.getLocaleMessage(DETAILS_DATES_CODE, DateUtils.dateAsRange(startDate, endDate));
    }

    public static String seatsDetails(int seats, LocalMessageSource lms) {
        return seats != 0 ?
                lms.getLocaleMessage(DETAILS_SEATS_CODE, String.valueOf(seats)) :
                DETAILS_EMPTY;
    }

    public static String deliveryDetails(int packageSize, LocalMessageSource lms) {
        return packageSize != 0 ?
                DETAILS_EMPTY :
                DETAILS_EMPTY;
    }
}
