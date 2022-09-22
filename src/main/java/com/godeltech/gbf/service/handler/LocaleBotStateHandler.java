package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.keyboard.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class LocaleBotStateHandler implements BotStateHandler {
    protected LocalMessageSource localMessageSource;

    protected Keyboard keyboard;
    protected LocalAnswerService localAnswerService;

    public LocaleBotStateHandler(LocalMessageSource localMessageSource, Keyboard keyboard, LocalAnswerService localAnswerService) {
        this.localMessageSource = localMessageSource;
        this.keyboard = keyboard;
        this.localAnswerService = localAnswerService;
    }

    protected SendMessage createMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        return callback;
    }

    @Override
    public SendMessage getView(Long chatId, Long userId, String callback) {
        UserData cachedUserData = UserDataCache.get(userId);
        return SendMessage.builder().
                parseMode("html").
                chatId(chatId).
                text(localAnswerService.getTextAnswer(cachedUserData)).
                replyMarkup(keyboard.getKeyboardMarkup(callback)).
                build();
    }
}
