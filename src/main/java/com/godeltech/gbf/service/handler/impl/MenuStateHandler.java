package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.MenuKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class MenuStateHandler extends LocaleBotStateHandler {

    public MenuStateHandler(LocaleMessageSource localeMessageSource, MenuKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        User telegramUser = update.hasCallbackQuery() ?
                update.getCallbackQuery().getFrom() : update.getMessage().getFrom();
        UserData userData = new UserData();
        userData.setId(telegramUser.getId());
        userData.setUsername(telegramUser.getUserName());
        userData.setBotState(BotState.MENU);
        UserDataCache.add(userData.getId(), userData);
    }
}
