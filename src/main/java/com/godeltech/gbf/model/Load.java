package com.godeltech.gbf.model;

import com.godeltech.gbf.LocaleMessageSource;

import java.util.HashMap;
import java.util.Map;

public enum Load {
    DOCUMENTS, THINGS, PEOPLE;

    public static final Map<Load, String> descriptions = new HashMap<>();

    static {
        descriptions.put(DOCUMENTS, "documents");
        descriptions.put(THINGS, "things");
        descriptions.put(PEOPLE, "people");
    }

    public String getDescription(LocaleMessageSource localeMessageSource) {
        return localeMessageSource.getLocaleMessage(descriptions.get(this));
    }
}
