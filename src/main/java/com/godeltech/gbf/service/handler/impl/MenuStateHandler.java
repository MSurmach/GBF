package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.MenuKeyboard;
import org.springframework.stereotype.Service;

@Service
public class MenuStateHandler extends LocaleBotStateHandler {

    public MenuStateHandler(LocalMessageSource localMessageSource, MenuKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        UserData newUserData = new UserData();
        newUserData.setId(userId);
        newUserData.setUsername(callback);
        newUserData.setCurrentState(State.MENU);
        newUserData.setPreviousState(State.MENU);
        UserDataCache.add(userId, newUserData);
        return callback;
    }
}
