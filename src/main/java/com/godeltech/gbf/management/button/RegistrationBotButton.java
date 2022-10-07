package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum RegistrationBotButton implements BotButton {
    REGISTRATION_EDIT, REGISTRATION_DELETE, REGISTRATION_FIND_CLIENTS;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
