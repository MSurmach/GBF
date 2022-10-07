package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum EditorBotButton implements BotButton {
    EDIT_COUNTRY_CITY_FROM, EDIT_COUNTRY_CITY_TO,
    EDIT_DATE_FROM, ADD_DATE_FROM, DELETE_DATE_FROM,
    EDIT_DATE_TO, ADD_DATE_TO, DELETE_DATE_TO,
    EDIT_COMMENT, ADD_COMMENT, DELETE_COMMENT,
    EDIT_CARGO, EDIT_CONFIRM;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
