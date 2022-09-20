package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.MonthKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MonthStateHandler extends LocaleBotStateHandler {

    public MonthStateHandler(LocaleMessageSource localeMessageSource, MonthKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramUserId = callbackQuery.getFrom().getId();
        String callBackData = callbackQuery.getData();
        UserData cachedUserData = UserDataCache.get(telegramUserId);
        BotState currentBotState = cachedUserData.getCurrentBotState();
        if (currentBotState == BotState.MONTH_TO) cachedUserData.setMonthTo(callBackData);
        else cachedUserData.setMonthFrom(callBackData);
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        cachedUserData.setCurrentBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Long chatId, Long userId) {
        return null;
    }
}
