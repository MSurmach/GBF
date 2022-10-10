package com.godeltech.gbf.gui.text.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.gui.text.Text;
import org.springframework.stereotype.Component;

@Component
public class MenuText implements Text {

    private final static String MENU_CODE = "menu";
    private LocalMessageSource localMessageSource;

    public MenuText(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getText(UserData userData) {
        return localMessageSource.getLocaleMessage(MENU_CODE, userData.getUsername());
    }
}
