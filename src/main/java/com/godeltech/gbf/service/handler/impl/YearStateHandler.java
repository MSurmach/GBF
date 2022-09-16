package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.KeyboardMarkupAppender;
import com.godeltech.gbf.service.keyboard.impl.CalendarKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class YearStateHandler extends LocaleBotStateHandler {

    private Keyboard keyboard;

    public YearStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Autowired
    public void setKeyboard(CalendarKeyboard keyboard) {
        this.keyboard = keyboard;
    }
//    @Autowired
//    public void setKeyboard(YearKeyboard keyboard) {
//        this.keyboard = keyboard;
//    }

    @Override
    public void handleUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramUserId = callbackQuery.getFrom().getId();
        String callBackData = callbackQuery.getData();
        UserData cachedUserData = UserDataCache.get(telegramUserId);
        BotState currentBotState = cachedUserData.getBotState();
        if (currentBotState == BotState.YEAR_TO) cachedUserData.setYearTo(callBackData);
        else cachedUserData.setYearFrom(callBackData);
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        cachedUserData.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        UserData userDataFromCache = UserDataCache.get(callbackQuery.getFrom().getId());
        sendMessage.setText(textAnswer(userDataFromCache));
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.YEAR_TO ?
                localeMessageSource.getLocaleMessage("year.to", userData.getCountryTo(), userData.getCityTo()) :
                localeMessageSource.getLocaleMessage("year.from", userData.getCountryFrom(), userData.getCityFrom());
    }
}
