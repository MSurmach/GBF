package com.godeltech.gbf.gui.utils;

import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.db.Delivery;
import com.godeltech.gbf.model.db.RoutePoint;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.godeltech.gbf.gui.utils.ConstantUtil.*;

public class MessageUtils {

    public static String routeDetails(List<RoutePoint> route, LocalMessageSource lms) {
        return route.isEmpty() ? EMPTY :
                lms.getLocaleMessage(DETAILS_ROUTE_CODE, routeContent(route, true, lms));
    }

    public static String datesDetails(LocalDate startDate, LocalDate endDate, LocalMessageSource lms) {
        return Objects.isNull(startDate) && Objects.isNull(endDate) ? EMPTY :
                lms.getLocaleMessage(DETAILS_DATES_CODE, datesContent(startDate, endDate));
    }

    public static String deliveryDetails(Delivery delivery, LocalMessageSource lms) {
        return Objects.isNull(delivery) ?
                EMPTY :
                lms.getLocaleMessage(DETAILS_DELIVERY_CODE, deliveryContent(delivery, lms));
    }

    public static String commentDetails(String comment, LocalMessageSource lms) {
        return Objects.isNull(comment) ?
                EMPTY :
                lms.getLocaleMessage(DETAILS_COMMENT_CODE, comment);
    }

    public static String seatsDetails(int seats, LocalMessageSource lms) {
        return seats != 0 ?
                lms.getLocaleMessage(DETAILS_SEATS_CODE, String.valueOf(seats)) :
                EMPTY;
    }

    public static String routeContent(List<RoutePoint> route, boolean statusImage, LocalMessageSource lms) {
        final String arrow = " ➜ ";
        var stringBuilder = new StringBuilder();
        for (int index = 0; index < route.size(); index++) {
            RoutePoint routePoint = route.get(index);
            String localCityName = lms.getLocaleMessage(routePoint.getCity().getName());
            if (statusImage)
                stringBuilder.append(statusImage(routePoint, lms)).append(" ");
            stringBuilder.append(localCityName);
            if (index != route.size() - 1)
                stringBuilder.append(arrow);
        }
        return stringBuilder.toString();
    }

    public static String datesContent(LocalDate startDate, LocalDate endDate) {
        return startDate.equals(endDate) ?
                DateUtils.shortFormatDate(startDate) :
                DateUtils.dateAsRange(startDate, endDate);
    }

    public static String statusImage(RoutePoint routePoint, LocalMessageSource lms) {
        return switch (routePoint.getStatus()) {
            case INITIAL -> "\uD83D\uDEA9";
            case INTERMEDIATE -> lms.getLocaleMessage(String.valueOf(routePoint.getOrderNumber()));
            case FINAL -> "\uD83C\uDFC1";
        };
    }

    public static String deliveryContent(Delivery delivery, LocalMessageSource lms) {
        return lms.getLocaleMessage(delivery.nameWithDesc());
    }
}
