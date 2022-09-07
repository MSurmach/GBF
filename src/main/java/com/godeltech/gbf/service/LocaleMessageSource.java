package com.godeltech.gbf.service;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class LocaleMessageSource {
    private final Locale locale = Locale.UK;
    private final MessageSource messageSource;

    public LocaleMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocaleMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }
}
