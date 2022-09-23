package com.godeltech.gbf.controls;

import lombok.Getter;

@Getter
public enum State {
    MENU, REGISTRATIONS, BACK, CONFIRMATION, WRONG_INPUT, SUCCESS,
    COUNTRY_FROM, COUNTRY_TO,
    CITY_FROM, CITY_TO,
    YEAR_FROM, YEAR_TO,
    MONTH_FROM, MONTH_TO,
    DATE_FROM, DATE_TO,
    CARGO,
    USERS_LIST,
    PACKAGE,
    PEOPLE
}
