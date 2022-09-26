package com.godeltech.gbf.management;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

@Getter
public enum State implements LocalMessage {
    MENU, REGISTRATIONS, CONFIRMATION, WRONG_INPUT, SUCCESS, COMMENT,
    COUNTRY_FROM, COUNTRY_TO,
    CITY_FROM, CITY_TO,
    YEAR_FROM, YEAR_TO,
    MONTH_FROM, MONTH_TO,
    DATE_FROM, DATE_TO,
    CARGO_MENU, CARGO_PACKAGE, CARGO_PEOPLE,
    USERS_LIST;

    @Override
    public String getLocalMessage(LocalMessageSource localMessageSource, String... args) {
        return localMessageSource.getLocaleMessage(this.name(), args);
    }
}
