package com.godeltech.gbf.service.answer;

import com.godeltech.gbf.LocalMessageSource;

public abstract class LocalAnswerService implements AnswerService {
    protected LocalMessageSource localMessageSource;

    protected LocalAnswerService(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }
}
