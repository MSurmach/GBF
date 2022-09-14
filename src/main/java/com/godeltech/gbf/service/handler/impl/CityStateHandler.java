package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeybordAddData;
import com.godeltech.gbf.service.keyboard.impl.CityKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CityStateHandler extends LocaleBotStateHandler {

    private Keyboard keyboard;

    @Autowired
    public void setKeyboard(CityKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public CityStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {
        Long telegramUserId = update.getCallbackQuery().getFrom().getId();
        String callBackData = update.getCallbackQuery().getData();
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(telegramUserId);
        BotState currentBotState = userDataFromCache.getBotState();
        if (currentBotState == BotState.CITY_TO) userDataFromCache.setCityTo(callBackData);
        else userDataFromCache.setCityFrom(callBackData);
        BotStateFlow botStateFlow = userDataFromCache.getBotStateFlow();
        userDataFromCache.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(update.getCallbackQuery().getFrom().getId());
        sendMessage.setText(textAnswer(userDataFromCache));
        BotState botState = userDataFromCache.getBotState();

        if (keyboard instanceof KeybordAddData keyboardWithData) {
            if (botState == BotState.CITY_FROM) {
                keyboardWithData.addDataToKeyboard(userDataFromCache.getCountryFrom());
            } else keyboardWithData.addDataToKeyboard(userDataFromCache.getCountryTo());
        }
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.CITY_TO ?
                localeMessageSource.getLocaleMessage("city.to.message", userData.getCountryTo()) :
                localeMessageSource.getLocaleMessage("city.from.message", userData.getCountryFrom());
    }
}
