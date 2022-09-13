package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.Country;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityStateHandler implements BotStateHandler {

    private LocaleMessageSource localeMessageSource;

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
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
        if (botState == BotState.CITY_FROM) {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup(userDataFromCache.getCountryFrom()));
        } else {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup(userDataFromCache.getCountryTo()));
        }
        return sendMessage;
    }

    private String textAnswer(UserData userData) {
        BotState botState = userData.getBotState();
        return botState == BotState.CITY_TO ?
                localeMessageSource.getLocaleMessage("city.to.message", userData.getCountryTo()) :
                localeMessageSource.getLocaleMessage("city.from.message", userData.getCountryFrom());
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup(String countryName) {
        Country country = Country.valueOf(countryName);
        List<String> cities = country.getCities();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < cities.size(); ) {
            int countriesInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (countriesInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(cities.get(index));
                button.setCallbackData(cities.get(index));
                rowWithButtons.add(button);
                countriesInRow--;
                index++;
                if (index == cities.size()) break;
            }
            keyboard.add(rowWithButtons);
        }
        return new InlineKeyboardMarkup(keyboard);
    }
}
