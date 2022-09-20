package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.CountryKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CountryStateHandler extends LocaleBotStateHandler {

    public CountryStateHandler(LocaleMessageSource localeMessageSource, CountryKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        Long telegramUserId = update.getCallbackQuery().getFrom().getId();
        String callBackData = update.getCallbackQuery().getData();
        UserData userDataFromCache = UserDataCache.get(telegramUserId);
        BotState currentBotState = userDataFromCache.getCurrentBotState();
        if (currentBotState == BotState.COUNTRY_TO) userDataFromCache.setCountryTo(callBackData);
        else userDataFromCache.setCountryFrom(callBackData);
        BotStateFlow botStateFlow = userDataFromCache.getBotStateFlow();
        userDataFromCache.setCurrentBotState(botStateFlow.getNextState(currentBotState));
    }
}
