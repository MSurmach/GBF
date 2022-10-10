package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import org.springframework.stereotype.Component;

@Component
public class YearText implements Text {
    private final static String YEAR_CODE = "year";
    private LocalMessageSource localMessageSource;

    public YearText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(YEAR_CODE);
    }
}
