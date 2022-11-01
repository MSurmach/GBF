package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.validator.exceptions.GbfException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteValidator {
    public final static String ALERT_ROUTE_HAS_LESS_THEN_TWO_POINTS_CODE = "alert.route.hasLessThen2Points";
    private LocalMessageSource lms;

    public void checkRouteHasLessThan2Points(List<RoutePoint> route, String callbackQueryId) {
        String alertMessage = lms.getLocaleMessage(ALERT_ROUTE_HAS_LESS_THEN_TWO_POINTS_CODE);
        if (route.size() < 2)
            throw new GbfException(callbackQueryId, alertMessage);
    }
}

