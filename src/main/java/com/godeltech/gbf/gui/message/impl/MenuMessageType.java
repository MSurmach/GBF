package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuMessageType implements MessageType {

    private final static String MENU_CODE = "menu";
    private final LocalMessageSource localMessageSource;

    @Override
    public State getState() {
        return State.MENU;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return localMessageSource.getLocaleMessage(MENU_CODE, sessionData.getUsername());
    }
}
