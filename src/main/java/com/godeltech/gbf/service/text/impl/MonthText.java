package com.godeltech.gbf.service.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import org.springframework.stereotype.Component;

@Component
public class MonthText implements Text {
    private final static String MONTH_CODE = "month";
    private final LocalMessageSource localMessageSource;

    public MonthText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
