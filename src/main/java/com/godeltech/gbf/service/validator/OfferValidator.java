package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@AllArgsConstructor
public class OfferValidator {
    private final LocalMessageSource lms;
    public final static String ALERT_ROUTE_EMPTY_CODE = "alert.route.empty";
    public final static String ALERT_CARGO_EMPTY_CODE = "alert.cargo.empty";

    public final static String ALERT_DATES_EMPTY_CODE = "alert.dates.empty";

    public void validateOfferBeforeSave(UserData userData) {
        LinkedList<RoutePoint> routePoints = userData.getRoutePoints();
        String callbackQueryId = userData.getCallbackQueryId();
        checkIfRoutePointsAreEmpty(routePoints, callbackQueryId).
                checkIfInitialRoutePointExists(routePoints, callbackQueryId).
                checkIfFinalRoutePointExists(routePoints, callbackQueryId).
                checkIfCargoIsNotEmpty(userData, callbackQueryId);
    }

    public OfferValidator checkIfRoutePointsAreEmpty(UserData userData) {
        LinkedList<RoutePoint> routePoints = userData.getRoutePoints();
        if (routePoints.isEmpty()) {
            String alertMessage = lms.getLocaleMessage(ALERT_ROUTE_EMPTY_CODE);
            throw new GbfException(userData.getCallbackQueryId(), alertMessage);
        }
        return this;
    }

    public OfferValidator checkIfDatesAreEmpty(UserData userData) {
        if (userData.getStartDate() == null) {
            String alertMessage = lms.getLocaleMessage(ALERT_DATES_EMPTY_CODE);
            throw new GbfException(userData.getCallbackQueryId(), alertMessage);
        }
        return this;
    }

//    public OfferValidator checkIfInitialRoutePointExists(List<RoutePoint> routePoints, String callbackQueryId) {
//        boolean initialPointExists = routePoints.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.INITIAL);
//        if (!initialPointExists) {
//            String alertMessage = lms.getLocaleMessage(ALERT_INITIAL_ROUTE_EMPTY_CODE);
//            throw new GbfException(callbackQueryId, alertMessage);
//        }
//        return this;
//    }
//
//    public OfferValidator checkIfFinalRoutePointExists(List<RoutePoint> routePoints, String callbackQueryId) {
//        boolean finalPointExists = routePoints.stream().anyMatch(routePoint -> routePoint.getStatus() == Status.FINAL);
//        if (!finalPointExists) {
//            String alertMessage = lms.getLocaleMessage(ALERT_FINAL_ROUTE_EMPTY_CODE);
//            throw new GbfException(callbackQueryId, alertMessage);
//        }
//        return this;
//    }

    public void checkIfCargoIsNotEmpty(UserData userData, String callbackQueryId) {
        if (userData.isCargoEmpty()) {
            String alertMessage = lms.getLocaleMessage(ALERT_CARGO_EMPTY_CODE);
            throw new GbfException(callbackQueryId, alertMessage);
        }
    }
}
