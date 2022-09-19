package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.impl.CityKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CityStateHandler extends LocaleBotStateHandler {

    public CityStateHandler(LocaleMessageSource localeMessageSource, CityKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {
        Long telegramUserId = update.getCallbackQuery().getFrom().getId();
        String callBackData = update.getCallbackQuery().getData();
        UserData userDataFromCache = UserDataCache.get(telegramUserId);
        BotState currentBotState = userDataFromCache.getBotState();
        if (currentBotState == BotState.CITY_TO) userDataFromCache.setCityTo(callBackData);
        else userDataFromCache.setCityFrom(callBackData);
        BotStateFlow botStateFlow = userDataFromCache.getBotStateFlow();
        userDataFromCache.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Long chatId, Long userId) {
        UserData cachedUserData = UserDataCache.get(userId);
        BotState botState = cachedUserData.getBotState();
        if (keyboard instanceof KeybordAddData keyboardWithData) {
            if (botState == BotState.CITY_FROM) {
                keyboardWithData.addDataToKeyboard(cachedUserData.getCountryFrom());
            } else keyboardWithData.addDataToKeyboard(cachedUserData.getCountryTo());
        }
        return SendMessage.builder().
                chatId(chatId).
                text(localAnswerService.getTextAnswer(cachedUserData)).
                replyMarkup(keyboard.getKeyboardMarkup()).
                build();
    }
}
