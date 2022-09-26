package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CargoPeopleAnswer implements Answer {

    private final static String CARGO_PEOPLE_CODE = "cargo.people";
    private final LocalMessageSource localMessageSource;

    public CargoPeopleAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData, List<UserData>... users) {
        return localMessageSource.getLocaleMessage(CARGO_PEOPLE_CODE);
    }
}
