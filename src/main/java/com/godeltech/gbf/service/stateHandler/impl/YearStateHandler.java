package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Calendar;
import java.util.List;

@Service
public class YearStateHandler implements BotStateHandler {
    private LocaleMessageSource localeMessageSource;

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Override
    public void handleUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramUserId = callbackQuery.getFrom().getId();
        String callBackData = callbackQuery.getData();
        UserData cachedUserData = UserDataCache.getUserDataFromCache(telegramUserId);
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
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(callbackQuery.getFrom().getId());
        sendMessage.setText(textAnswer(userDataFromCache));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.YEAR_TO ?
                localeMessageSource.getLocaleMessage("year.to.message", userData.getCountryTo(), userData.getCityTo()) :
                localeMessageSource.getLocaleMessage("year.from.message", userData.getCountryFrom(), userData.getCityFrom());
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        var currentYearButton = new InlineKeyboardButton(String.valueOf(currentYear));
        currentYearButton.setCallbackData(String.valueOf(currentYear));

        int nextYear = currentYear + 1;
        var nextYearButton = new InlineKeyboardButton(String.valueOf(nextYear));
        nextYearButton.setCallbackData(String.valueOf(currentYear));

        List<InlineKeyboardButton> buttons = List.of(currentYearButton, nextYearButton);
        return new InlineKeyboardMarkup(List.of(buttons));
    }
}
