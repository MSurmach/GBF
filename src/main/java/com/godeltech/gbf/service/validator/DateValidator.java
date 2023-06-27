package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.utils.DateUtils;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.service.validator.exceptions.GbfAlertException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@AllArgsConstructor
public class DateValidator {

  public final static String ALERT_DATE_PAST_CODE = "alert.date.past";
  public final static String ALERT_DATE_EMPTY_COURIER_CODE = "alert.dates.empty.courier";
  public final static String ALERT_DATE_EMPTY_CLIENT_CODE = "alert.dates.empty.client";
  private final LocalMessageSourceFactory localMessageSourceFactory;

  public void checkPastInDate(LocalDate date, String callbackQueryId, String language) {
    LocalDate nowDate = LocalDate.now();
    if (date.isBefore(nowDate)) {
      LocalMessageSource lms = localMessageSourceFactory.get(language);
      String alertMessage = lms.getLocaleMessage(
          ALERT_DATE_PAST_CODE,
          DateUtils.shortFormatDate(nowDate),
          DateUtils.shortFormatDate(date));
      throw new GbfAlertException(callbackQueryId, alertMessage);
    }
  }

  public void checkIfDatesExist(Session session) {
    LocalMessageSource lms = localMessageSourceFactory.get(session.getTelegramUser().getLanguage());
    Role role = session.getRole();
    String callbackQueryId = session.getCallbackQueryId();
    if (Objects.isNull(session.getStartDate())) {
      if (role == Role.COURIER) {
        String alertMessage = lms.getLocaleMessage(ALERT_DATE_EMPTY_COURIER_CODE);
        throw new GbfAlertException(callbackQueryId, alertMessage);
      }
      if (role == Role.CLIENT) {
        session.setReloadNeeded(true);
        LocalDate requiredStartDate = LocalDate.now();
        LocalDate requiredEndDate = requiredStartDate.plusMonths(1);
        session.setStartDate(requiredStartDate);
        session.setEndDate(requiredEndDate);
        String alertMessage = lms.getLocaleMessage(ALERT_DATE_EMPTY_CLIENT_CODE, requiredStartDate.toString(), requiredEndDate.toString());
        throw new GbfAlertException(callbackQueryId, alertMessage);
      }
    }
  }
}
