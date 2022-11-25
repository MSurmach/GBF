package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.exception.InsufficientInputException;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.factory.impl.HandlerTypeFactory;
import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
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

import static com.godeltech.gbf.model.State.*;

@Service
@Slf4j
public class MessageTextInterceptor extends AbstractInterceptor {

    public MessageTextInterceptor(HandlerTypeFactory handlerTypeFactory, View<? extends BotApiMethod<?>> view, BotMessageService botMessageService, TelegramUserService telegramUserService, SessionCacheService sessionCacheService, LocalMessageSourceFactory localMessageSourceFactory) {
        super(handlerTypeFactory, botMessageService, telegramUserService, sessionCacheService, view, localMessageSourceFactory);
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE_TEXT;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        User from = message.getFrom();
        log.info("Get message from user : {} with id : {} ", from.getUserName(), from.getId());
        chatId = message.getChatId();
        telegramUserId = from.getId();
        botMessageService.save(telegramUserId, message);
        Session session = connectSession(telegramUserId, from.getUserName());
        State state = interceptSufficientInput(update, session);
        session.getStateHistory().push(state);
        session.getCallbackHistory().push(update.getMessage().getText());
        return view.buildView(chatId, session);
    }

    private State interceptSufficientInput(Update update, Session session) throws InsufficientInputException {
        log.info("Intercept sufficient input by user with id:{}", telegramUserId);
        State currentState = session.getStateHistory().peek();
        if (currentState == SEATS || currentState == COMMENT || currentState == FEEDBACK || currentState == OFFER_ID_INPUT || currentState== FEEDBACK_DELETE_BY_ID) {
            String text = update.getMessage().getText();
            session.getCallbackHistory().push(text);
            HandlerType handlerType = handlerTypeFactory.get(currentState);
            return handlerType.handle(session);
        } else throw new WrongInputException();
    }
}
