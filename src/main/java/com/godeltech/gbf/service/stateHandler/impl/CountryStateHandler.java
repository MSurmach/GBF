package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.Country;
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
    public SendMessage handleUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Choose the country");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup());
        return sendMessage;
    }

    public SendMessage getView(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textAnswer());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer() {
        return localeMessageSource.getLocaleMessage("city.message");
    }

    private InlineKeyboardMarkup inlineKeyboardMarkup() {
        List<Country> countryList = List.of(Country.values());
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int index = 0; index < countryList.size(); ) {
            int countriesInRow = 3;
            List<InlineKeyboardButton> rowWithButtons = new ArrayList<>();
            while (countriesInRow > 0) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(countryList.get(index).name());
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
