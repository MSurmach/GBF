package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum State {
    BACK,
    FORM,
    MENU, SUCCESS_REGISTRATION, WRONG_INPUT,
    COMMENT,
    ROUTE,
    YEAR, MONTH, DATE,
    DELIVERY, SEATS,
    MY_OFFERS, ALL_OFFERS,
    COURIERS_SEARCH_RESULT, CLIENTS_SEARCH_RESULT,
    NON_USABLE,
    FEEDBACK, ALL_FEEDBACKS
}
