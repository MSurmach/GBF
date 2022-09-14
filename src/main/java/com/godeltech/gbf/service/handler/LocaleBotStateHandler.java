package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.LocaleMessageSource;

public abstract class LocaleBotStateHandler implements BotStateHandler {
    protected LocaleMessageSource localeMessageSource;

    public LocaleBotStateHandler(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}
