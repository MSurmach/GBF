package com.godeltech.gbf.localization.impl;

import com.godeltech.gbf.localization.AllMessageSource;
import com.godeltech.gbf.localization.LocalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BeMessageSource extends AllMessageSource implements LocalMessageSource {
    private final Locale BELARUSIAN = new Locale("be");

    public BeMessageSource(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public String getLocaleMessage(String code) {
        return messageSource.getMessage(code, null, BELARUSIAN);
    }

    @Override
    public String getLocaleMessage(String code, String... args) {
        return messageSource.getMessage(code, args, BELARUSIAN);
    }

    @Override
    public String getLanguage() {
        return BELARUSIAN.getLanguage();
    }

    @Override
    public Locale getLocale() {
        return BELARUSIAN;
    }
}
