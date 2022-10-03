package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public enum NavigationBotButton implements BotButton {
    GLOBAL_BACK, LOCAL_BACK, MENU;

    @Override
    public String localLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.name());
    }
}
