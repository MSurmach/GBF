package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum RegistrationBotButton implements BotButton {
    REGISTRATION_EDIT, REGISTRATION_DELETE;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
