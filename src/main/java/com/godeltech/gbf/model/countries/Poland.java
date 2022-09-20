package com.godeltech.gbf.model.countries;

import com.godeltech.gbf.LocaleMessageSource;

public enum Poland implements Country {
    BIALYSTOK, GDANSK, KRAKOW, LODZ, POZNAN, WARSAW, WROCLAW;

    @Override
    public String getLocaleDescription(LocaleMessageSource localeMessageSource) {
        return null;
    }
}