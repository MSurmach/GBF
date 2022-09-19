package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ConfirmKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ConfirmStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;

    public ConfirmStateHandler(LocaleMessageSource localeMessageSource, ConfirmKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handleUpdate(Update update) {
        UserData cachedUserData = UserDataCache.get(update.getCallbackQuery().getFrom().getId());
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        if (botStateFlow == BotStateFlow.COURIER) {
            User user = new User(cachedUserData);
            userRepository.save(user);
        }
        cachedUserData.setBotState(botStateFlow.getNextState(cachedUserData.getBotState()));
    }
}
