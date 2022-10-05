package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum CommonButton implements BotButton {
    CONFIRM;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
