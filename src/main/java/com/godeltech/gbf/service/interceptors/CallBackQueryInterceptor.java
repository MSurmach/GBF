package com.godeltech.gbf.service.interceptors;

import com.godeltech.gbf.SendMessageBuilder;
import com.godeltech.gbf.service.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.KeyboardFactory;
import com.godeltech.gbf.state.BotState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallBackQueryInterceptor implements Interceptor {

    @Override
    public BotApiMethod<?> intercept(Update update, LocaleMessageSource localeMessageSource, SendMessageBuilder sendMessageBuilder) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long userID = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        KeyboardFactory keyboardFactory = KeyboardFactory.getKeyboardFactory();
        keyboardFactory.setLocaleMessageSource(localeMessageSource);
        InlineKeyboardMarkup keyBoardMarkup = keyboardFactory.getKeyboard(BotState.COUNTRY_TO).getKeyBoardMarkup();
        return sendMessageBuilder.chat_id(chatId).keyBoardMarkup(keyBoardMarkup).build();
    }
}
