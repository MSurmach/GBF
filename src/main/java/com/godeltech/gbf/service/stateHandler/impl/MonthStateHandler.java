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

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MonthStateHandler implements BotStateHandler {
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
        if (currentBotState == BotState.MONTH_TO) cachedUserData.setMonthTo(callBackData);
        else cachedUserData.setMonthFrom(callBackData);
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
        sendMessage.setReplyMarkup(inlineKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.MONTH_TO ? localeMessageSource.getLocaleMessage("month.to.message", userData.getCountryTo(), userData.getCityTo()) : localeMessageSource.getLocaleMessage("month.from.message", userData.getCountryFrom(), userData.getCityFrom());
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<String> months = Arrays.asList(DateFormatSymbols.getInstance().getMonths());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < months.size(); ) {
            int monthsInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (monthsInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(months.get(index));
                button.setCallbackData(months.get(index));
                rowWithButtons.add(button);
                monthsInRow--;
                index++;
                if (index == months.size()) break;
            }
            keyboard.add(rowWithButtons);
        }
        return new InlineKeyboardMarkup(keyboard);
    }
}
