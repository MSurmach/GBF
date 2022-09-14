package com.godeltech.gbf.service.keyboard;

import com.godeltech.gbf.LocaleMessageSource;

public abstract class LocaleKeyboard implements Keyboard {
    protected LocaleMessageSource localeMessageSource;

    public LocaleKeyboard(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}
