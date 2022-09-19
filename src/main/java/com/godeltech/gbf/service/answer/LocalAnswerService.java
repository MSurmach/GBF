package com.godeltech.gbf.service.answer;

import com.godeltech.gbf.LocaleMessageSource;

public abstract class LocalAnswerService implements AnswerService {
    protected LocaleMessageSource localeMessageSource;

    protected LocalAnswerService(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}
