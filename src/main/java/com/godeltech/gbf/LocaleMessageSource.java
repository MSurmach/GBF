package com.godeltech.gbf;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class LocaleMessageSource {

    private Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public void setLocale(String languageCode) {
        this.locale = new Locale(languageCode);
    }

    public String getLocaleMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getLocaleMessage(String code, String... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
