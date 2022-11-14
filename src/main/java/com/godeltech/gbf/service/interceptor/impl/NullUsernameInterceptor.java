package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class NullUsernameInterceptor implements Interceptor {
    private final BotMessageService botMessageService;

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.USERNAME_NULL;
    }

    public NullUsernameInterceptor(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.hasMessage() ?
                update.getMessage() :
                update.getCallbackQuery().getMessage();
        telegramUserId = message.getFrom().getId();
        chatId = message.getChatId();
        botMessageService.checkBotMessage(message.getMessageId(), telegramUserId, chatId);
        return SendMessage.builder().
                text("Невозможно инициализировать сессию с ботом, так как username = null. Нужно указать username в настройках Telegram (нет возможности связаться с человеком у которого username - @null через л.с.)").
                replyMarkup(null).
                chatId(chatId).
                build();
    }
}
