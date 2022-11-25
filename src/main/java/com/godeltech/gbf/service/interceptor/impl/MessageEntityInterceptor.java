package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.factory.impl.HandlerTypeFactory;
import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.AbstractInterceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.view.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.godeltech.gbf.model.State.MENU;

@Service
@Slf4j
public class MessageEntityInterceptor extends AbstractInterceptor {

    public MessageEntityInterceptor(HandlerTypeFactory handlerTypeFactory, View<? extends BotApiMethod<?>> view, BotMessageService botMessageService, TelegramUserService telegramUserService, SessionCacheService sessionCacheService, LocalMessageSourceFactory localMessageSourceFactory) {
        super(handlerTypeFactory, botMessageService, telegramUserService, sessionCacheService, view, localMessageSourceFactory);
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE_ENTITY;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();
        log.info("Get message user user : {} with id : {} ", user.getUserName(), user.getId());
        chatId = message.getChatId();
        telegramUserId = user.getId();
        botMessageService.save(telegramUserId, message);
        Session session = connectSession(telegramUserId, user.getUserName());
        State nextState = interceptTextCommand(message.getText(), session);
        session.getStateHistory().push(nextState);
        session.getCallbackHistory().push(update.getMessage().getText());
        return view.buildView(chatId, session);
    }

    State interceptTextCommand(String command, Session session) throws TextCommandNotFoundException {
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        switch (TextCommand.valueOf(parsedAsCommand)) {
            case START -> {
                ModelUtils.resetSessionData(session);
                return MENU;
            }
            default -> throw new TextCommandNotFoundException();
        }
    }
}
