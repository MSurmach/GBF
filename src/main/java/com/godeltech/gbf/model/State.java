package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum State {
    BACK,
    FORM, INTERMEDIATE_EDITOR,
    MENU, SUCCESS_REGISTRATION, WRONG_INPUT,
    COMMENT,
    ROUTE,
    YEAR, MONTH, DATE,
    DELIVERY, SEATS,
    REGISTRATIONS, REQUESTS,
    COURIERS_LIST_RESULT, CLIENTS_LIST_RESULT,
    NON_USABLE
}
