package com.godeltech.gbf.localization;

public interface LocalMessageSource {
    String getLocaleMessage(String code);

    String getLocaleMessage(String code, String... args);

    String getLanguage();
}
