package com.godeltech.gbf.service.keyboard;

import com.godeltech.gbf.LocalMessageSource;

public abstract class LocaleKeyboard implements Keyboard {
    protected LocalMessageSource localMessageSource;

    public LocaleKeyboard(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }
}
