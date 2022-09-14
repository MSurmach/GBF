package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.Load;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.LoadKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CargoStateHandler extends LocaleBotStateHandler {
    private Keyboard keyboard;

    @Autowired
    public void setKeyboard(LoadKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public CargoStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramUserId = callbackQuery.getFrom().getId();
        String callBackData = callbackQuery.getData();
        UserData cachedUserData = UserDataCache.getUserDataFromCache(telegramUserId);
        BotState currentBotState = cachedUserData.getBotState();
        cachedUserData.setLoad(Load.valueOf(callBackData));
        BotStateFlow botStateFlow = cachedUserData.getBotStateFlow();
        cachedUserData.setBotState(botStateFlow.getNextState(currentBotState));
    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        UserData cachedUserData = UserDataCache.getUserDataFromCache(callbackQuery.getFrom().getId());
        sendMessage.setText(textAnswer(cachedUserData));
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotStateFlow botState = userData.getBotStateFlow();
        return botState == BotStateFlow.COURIER ? localeMessageSource.getLocaleMessage("cargo.courier.message") : localeMessageSource.getLocaleMessage("cargo.customer.message");
    }
}
