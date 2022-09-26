package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.stereotype.Service;

@Service
public class MenuStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {
        userData.setId(userId);
        userData.setCurrentState(State.MENU);
        userData.setPreviousState(State.MENU);
        UserDataCache.add(userId, userData);
    }
}
