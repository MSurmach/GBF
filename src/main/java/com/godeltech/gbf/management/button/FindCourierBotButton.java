package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum FindCourierBotButton implements BotButton {
    LOOK_AT_COURIERS;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
