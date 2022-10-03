package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum DateQuizBotButton implements BotButton {
    SELECT_DATE, SKIP_DATE;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
