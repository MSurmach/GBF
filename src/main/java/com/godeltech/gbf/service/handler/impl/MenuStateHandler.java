package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MENU;

@Service
public class MenuStateHandler implements StateHandler {

    @Override
    public State handle(UserData userData) {
        userData.reset();
        UserDataCache.initializeByIdAndUsername(userData.getTelegramUserId(), userData.getUsername());
        return MENU;
    }
}
