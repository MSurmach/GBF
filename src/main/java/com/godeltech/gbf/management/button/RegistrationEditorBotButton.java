package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum RegistrationEditorBotButton implements BotButton {
    EDIT_COUNTRY_CITY_FROM, EDIT_COUNTRY_CITY_TO,
    EDIT_DATE_FROM, EDIT_DATE_TO,
    EDIT_COMMENT, EDIT_CARGO, EDIT_CONFIRM;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
