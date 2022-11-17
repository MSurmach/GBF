package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LanguageHandlerType implements HandlerType {
    private final TelegramUserService telegramUserService;
    @Override
    public State getState() {
        return State.LANGUAGE;
    }

    @Override
    public State handle(SessionData sessionData) {
        String callback = sessionData.getCallbackHistory().peek();
        sessionData.setLanguage(callback);
        telegramUserService.saveUser(sessionData.getTelegramUserId(), sessionData.getUsername(), sessionData.getLanguage());
        return State.MENU;
    }
}
