package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import org.springframework.stereotype.Component;

@Component
public class CargoPeopleText implements Text {

    private final static String CARGO_PEOPLE_CODE = "cargo.people";
    private final LocalMessageSource localMessageSource;

    public CargoPeopleText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(CARGO_PEOPLE_CODE);
    }
}
