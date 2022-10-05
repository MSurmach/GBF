package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum MainMenuButton implements BotButton {
    COURIER, CUSTOMER, REGISTRATIONS_VIEWER;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
