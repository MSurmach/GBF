package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
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
        ModelUtils.resetSessionData(sessionData);
        SessionDataCache.initializeByIdAndUsernameAndFirstNameAndLastName(sessionData.getTelegramUserId(),
                sessionData.getUsername(),
                sessionData.getFirstName(),
                sessionData.getLastName());
        return MENU;
    }
}
