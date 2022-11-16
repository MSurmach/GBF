package com.godeltech.gbf.localization.impl;

import com.godeltech.gbf.localization.AllMessageSource;
import com.godeltech.gbf.localization.LocalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class EnMessageSource extends AllMessageSource implements LocalMessageSource {

    private final Locale ENGLISH = new Locale("en");

    public EnMessageSource(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public String getLocaleMessage(String code) {
        return messageSource.getMessage(code, null, ENGLISH);
    }

    @Override
    public String getLocaleMessage(String code, String... args) {
        return messageSource.getMessage(code, args, ENGLISH);
    }

    @Override
    public String getLanguage() {
        return ENGLISH.getLanguage();
    }

    @Override
    public Locale getLocale() {
        return ENGLISH;
    }
}
