package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.InsufficientInputException;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.godeltech.gbf.model.State.*;

@Service
@Slf4j
public class MessageTextInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final View<? extends BotApiMethod<?>> view;

    private final BotMessageService botMessageService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageTextInterceptor(HandlerFactory handlerFactory, View<SendMessage> view, BotMessageService botMessageService) {
        this.handlerFactory = handlerFactory;
        this.view = view;
        this.botMessageService = botMessageService;
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
        State state = interceptSufficientInput(update);
        SessionData cached = SessionDataCache.get(telegramUserId);
        cached.getStateHistory().push(state);
        cached.getCallbackHistory().push(update.getMessage().getText());
        return view.buildView(chatId, cached);
    }

    private State interceptSufficientInput(Update update) throws InsufficientInputException {
        log.info("Intercept sufficient input by user with id:{}", telegramUserId);
        SessionData cached = SessionDataCache.get(telegramUserId);
        if (cached == null) throw new InsufficientInputException();
        State currentState = cached.getStateHistory().peek();
        if (currentState == SEATS || currentState == COMMENT || currentState == FEEDBACK || currentState == OFFER_ID_INPUT) {
            String text = update.getMessage().getText();
            cached.getCallbackHistory().push(text);
            HandlerType handlerType = handlerFactory.get(currentState);
            return handlerType.handle(cached);
        } else throw new WrongInputException();
    }
}
