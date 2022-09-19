package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.impl.DayKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class DayStateHandler extends LocaleBotStateHandler {

    public DayStateHandler(LocaleMessageSource localeMessageSource, DayKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramUserId = callbackQuery.getFrom().getId();
        String callBackData = callbackQuery.getData();
        UserData cachedUserData = UserDataCache.get(telegramUserId);
        BotState currentBotState = cachedUserData.getBotState();
        if (currentBotState == BotState.DAY_FROM) cachedUserData.setDayFrom(callBackData);
        else cachedUserData.setDayTo(callBackData);
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        cachedUserData.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Long chatId, Long userId) {
        UserData cachedUserData = UserDataCache.get(userId);
        BotState botState = cachedUserData.getBotState();
        String answer = localAnswerService.getTextAnswer(cachedUserData);
        if (keyboard instanceof KeybordAddData keyboardWithData) {
            if (botState == BotState.DAY_TO) {
                keyboardWithData.addDataToKeyboard(cachedUserData.getYearTo(), cachedUserData.getMonthTo());
            } else keyboardWithData.addDataToKeyboard(cachedUserData.getYearFrom(), cachedUserData.getMonthFrom());
        }
        return createMessage(chatId, answer, keyboard.getKeyboardMarkup());
    }
}
