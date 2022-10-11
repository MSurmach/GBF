package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.Message;
import com.godeltech.gbf.model.UserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMessage implements Message {

    private final static String MENU_CODE = "menu";
    private final LocalMessageSource localMessageSource;

    @Override
    public String getMessage(UserData userData) {
        return localMessageSource.getLocaleMessage(MENU_CODE, userData.getUsername());
    }
}
