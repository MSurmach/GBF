package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.handler.HandlerType;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.MENU;

@Service
public class MenuHandlerType implements HandlerType {

    @Override
    public State getState() {
        return MENU;
    }

    @Override
    public State handle(SessionData sessionData) {
        ModelUtils.resetUserData(sessionData);
        UserDataCache.initializeByIdAndUsername(sessionData.getTelegramUserId(), sessionData.getUsername());
        return MENU;
    }
}
