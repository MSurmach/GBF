package com.godeltech.gbf.service.validator;

import com.godeltech.gbf.model.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FormValidator {

  private final RouteValidator routeValidator;
  private final DateValidator dateValidator;

  public void validateForm(Session session) {
    routeValidator.checkRouteIsNotEmpty(session);
    dateValidator.checkIfDatesExist(session);
  }

}

