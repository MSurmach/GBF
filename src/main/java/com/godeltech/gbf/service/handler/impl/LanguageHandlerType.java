package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Session;
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
    public State handle(Session session) {
        String callback = session.getCallbackHistory().peek();
        session.getTelegramUser().setLanguage(callback);
        telegramUserService.saveUser(session.getTelegramUser());
        return State.MENU;
    }
}
