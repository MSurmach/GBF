package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class YearMessage implements Message {
    private final static String YEAR_CODE = "year.question";
    private LocalMessageSource localMessageSource;

    public YearMessage(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(YEAR_CODE);
    }
}
