package com.godeltech.gbf.model.countries;

import com.godeltech.gbf.LocaleMessageSource;

public enum GreatBritain implements Country{
    LONDON, MANCHESTER;

    @Override
    public String getLocaleDescription(LocaleMessageSource localeMessageSource) {
        return null;
    }
}
