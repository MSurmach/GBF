package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.CountryKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CountryStateHandler extends LocaleBotStateHandler {

    private Keyboard keyboard;

    public CountryStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setKeyboard(CountryKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public void handleUpdate(Update update) {
        Long telegramUserId = update.getCallbackQuery().getFrom().getId();
        String callBackData = update.getCallbackQuery().getData();
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(telegramUserId);
        BotState currentBotState = userDataFromCache.getBotState();
        if (currentBotState == BotState.COUNTRY_TO) userDataFromCache.setCountryTo(callBackData);
        else userDataFromCache.setCountryFrom(callBackData);
        BotStateFlow botStateFlow = userDataFromCache.getBotStateFlow();
        userDataFromCache.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(update.getCallbackQuery().getFrom().getId());
        BotState botState = userDataFromCache.getBotState();
        sendMessage.setText(textAnswer(botState));
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(BotState botState) {
        return botState == BotState.COUNTRY_TO ?
                localeMessageSource.getLocaleMessage("country.to.message") :
                localeMessageSource.getLocaleMessage("country.from.message");
    }
}
