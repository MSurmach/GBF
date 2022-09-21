package com.godeltech.gbf.model;

import com.godeltech.gbf.LocaleMessageSource;

public enum Load {
    DOCUMENTS("documents"), THINGS("things"), PEOPLE("people");

    private final String description;

    Load(String description) {
        this.description = description;
    }

    public String getDescription(LocaleMessageSource localeMessageSource) {
        return localeMessageSource.getLocaleMessage(description);
    }
}
