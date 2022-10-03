package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum CalendarBotButton implements BotButton {
    INIT, PREVIOUS, NEXT, IGNORE,
    CHANGE_YEAR, SELECT_YEAR,
    CHANGE_MONTH, SELECT_MONTH,
    SELECT_DAY;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
