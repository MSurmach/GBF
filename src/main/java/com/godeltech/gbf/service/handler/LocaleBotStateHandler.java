package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.keyboard.Keyboard;
import org.apache.http.client.UserTokenHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class LocaleBotStateHandler implements BotStateHandler {
    protected LocaleMessageSource localeMessageSource;

    protected Keyboard keyboard;
    protected LocalAnswerService localAnswerService;

    public LocaleBotStateHandler(LocaleMessageSource localeMessageSource, Keyboard keyboard, LocalAnswerService localAnswerService) {
        this.localeMessageSource = localeMessageSource;
        this.keyboard = keyboard;
        this.localAnswerService = localAnswerService;
    }

    protected SendMessage createMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage getView(Long chatId, Long userId) {
        UserData cachedUserData = UserDataCache.get(userId);
        return createMessage(chatId, localAnswerService.getTextAnswer(cachedUserData), keyboard.getKeyboardMarkup());
    }
}
