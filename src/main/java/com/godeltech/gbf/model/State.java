package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum State {
    FORM, ROUTE_POINT_FORM,
    MENU, SUCCESS, WRONG_INPUT, SUMMARY_DATA_TO_CONFIRM,
    COMMENT_QUIZ, COMMENT, COMMENT_CONFIRM,
    COUNTRY_FROM, COUNTRY_TO,
    CITY_FROM, CITY_TO,
    YEAR_FROM, YEAR_TO,
    MONTH_FROM, MONTH_TO,
    DATE_FROM, DATE_TO, DATE_FROM_QUIZ, DATE_TO_QUIZ,
    CARGO_MENU, CARGO_PACKAGE, CARGO_PEOPLE,
    REGISTRATIONS, REGISTRATION_EDITOR,
    FOUND_COURIERS_INFO, COURIERS_LIST, CLIENTS_LIST,
    REQUESTS, REQUEST_EDITOR
}
