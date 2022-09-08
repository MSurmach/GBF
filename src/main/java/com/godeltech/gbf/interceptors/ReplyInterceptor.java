package com.godeltech.gbf.interceptors;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ReplyInterceptor {
    public BotApiMethod<?> interceptUpdate(Update update) {
        Message message = update.getMessage();
        Long telegramUserId = message.getFrom().getId();
        Long chatId = message.getChatId();
        return null;
    }
}
