package com.godeltech.gbf.model;

import lombok.Getter;

@Getter
public enum State {
    MENU,
    REGISTRATIONS,
    COUNTRY_FROM,
    CITY_FROM,
    YEAR_FROM,
    MONTH_FROM,
    DAY_FROM,
    COUNTRY_TO,
    CITY_TO,
    YEAR_TO,
    MONTH_TO,
    DAY_TO,
    LOAD,
    HELP,
    FINISH,
    BACK,
    CONFIRM,
    WRONG_INPUT,
    SUCCESS,
    USERS_LIST,
    DATE_FROM,
    DATE_TO;

    private String callback = null;

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
