package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.impl.DayKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class DayStateHandler extends LocaleBotStateHandler {

    private Keyboard keyboard;

    public DayStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setKeyboard(DayKeyboard keyboard) {
        this.keyboard = keyboard;
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
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        UserData cachedUserData = UserDataCache.get(callbackQuery.getFrom().getId());
        sendMessage.setText(textAnswer(cachedUserData));
        BotState botState = cachedUserData.getBotState();
        if (keyboard instanceof KeybordAddData keyboardWithData) {
            if (botState == BotState.DAY_TO) {
                keyboardWithData.addDataToKeyboard(cachedUserData.getYearTo(), cachedUserData.getMonthTo());
            } else keyboardWithData.addDataToKeyboard(cachedUserData.getYearFrom(), cachedUserData.getMonthFrom());
        }
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.DAY_TO ? localeMessageSource.getLocaleMessage("day.to", userData.getCountryTo(), userData.getCityTo()) : localeMessageSource.getLocaleMessage("day.from", userData.getCountryFrom(), userData.getCityFrom());
    }
}
