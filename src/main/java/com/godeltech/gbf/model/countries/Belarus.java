package com.godeltech.gbf.model.countries;

import com.godeltech.gbf.LocaleMessageSource;

public enum Belarus implements Country {
    BREST, VITEBSK, GOMEL, GRODNO, MOGILEV, MINSK;

    @Override
    public String getLocaleDescription(LocaleMessageSource localeMessageSource) {
        return null;
    }
}
