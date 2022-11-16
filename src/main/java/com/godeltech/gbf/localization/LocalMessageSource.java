package com.godeltech.gbf.localization;

import java.util.Locale;

public interface LocalMessageSource {
    String getLocaleMessage(String code);

    String getLocaleMessage(String code, String... args);

    String getLanguage();

    Locale getLocale();
}
