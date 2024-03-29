package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.RoutePoint;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteValidator {

  public final static String ALERT_ROUTE_HAS_LESS_THEN_TWO_POINTS_CODE = "alert.route.hasMoreOrEqualsThen2Points";
  public final static String ALERT_ROUTE_EMPTY_CODE = "alert.route.empty";
  private final LocalMessageSourceFactory localMessageSourceFactory;
    private final ApplicationContext applicationContext;

  public void checkRouteIsNotEmpty(Session session) {
    LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
    String alertMessage = lms.getLocaleMessage(ALERT_ROUTE_EMPTY_CODE);
    if (session.getRoute().isEmpty()) {
      throw new GbfAlertException(session.getCallbackQueryId(), alertMessage);
    }
  }

  public void checkRouteHasMoreOrEqualsThan2Points(List<RoutePoint> route, String callbackQueryId, String language) {
    LocalMessageSource lms = localMessageSourceFactory.get(language);
    String alertMessage = lms.getLocaleMessage(ALERT_ROUTE_HAS_LESS_THEN_TWO_POINTS_CODE);
    if (route.size() < 2)
      throw new GbfAlertException(callbackQueryId, alertMessage);
  }
}