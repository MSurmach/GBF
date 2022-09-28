package com.godeltech.gbf.service.answer.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuAnswer implements Answer {

    private final static String MENU_CODE = "menu";
    private LocalMessageSource localMessageSource;

    public MenuAnswer(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public String getAnswer(UserData userData) {
        return localMessageSource.getLocaleMessage(MENU_CODE, userData.getUsername());
    }
}
