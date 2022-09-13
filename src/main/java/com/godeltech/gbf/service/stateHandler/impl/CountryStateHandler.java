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
public class CountryStateHandler implements BotStateHandler {

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
        sendMessage.setReplyMarkup(inlineKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(BotState botState) {
        return botState == BotState.COUNTRY_TO ?
                localeMessageSource.getLocaleMessage("country.to.message") :
                localeMessageSource.getLocaleMessage("country.from.message");
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<Country> countryList = List.of(Country.values());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < countryList.size(); ) {
            int countriesInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (countriesInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(countryList.get(index).getName());
                button.setCallbackData(countryList.get(index).name());
                rowWithButtons.add(button);
                countriesInRow--;
                index++;
                if (index == countryList.size()) break;
            }
            keyboard.add(rowWithButtons);
        }
        return new InlineKeyboardMarkup(keyboard);
    }


}
