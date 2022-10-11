package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.message.Message;
import org.springframework.stereotype.Component;

@Component
public class MonthMessage implements Message {
    private final static String MONTH_CODE = "month";
    private final LocalMessageSource localMessageSource;

    public MonthMessage(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
