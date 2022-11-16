package com.godeltech.gbf.localization.impl;

import com.godeltech.gbf.localization.AllMessageSource;
import com.godeltech.gbf.localization.LocalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RuMessageSource extends AllMessageSource implements LocalMessageSource {
    private final Locale RUSSIAN = new Locale("ru");

    public RuMessageSource(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public String getLocaleMessage(String code) {
        return messageSource.getMessage(code, null, RUSSIAN);
    }

    @Override
    public String getLocaleMessage(String code, String... args) {
        return messageSource.getMessage(code, args, RUSSIAN);
    }

    @Override
    public String getLanguage() {
        return RUSSIAN.getLanguage();
    }
}
