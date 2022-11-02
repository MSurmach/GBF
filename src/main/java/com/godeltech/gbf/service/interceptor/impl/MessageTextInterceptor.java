package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.InsufficientInputException;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.factory.impl.ViewFactory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static com.godeltech.gbf.model.State.COMMENT;
import static com.godeltech.gbf.model.State.SEATS;

@Service
@Slf4j
public class MessageTextInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final ViewFactory viewFactory;

    private final BotMessageService botMessageService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageTextInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory, BotMessageService botMessageService) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
        this.botMessageService = botMessageService;
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE_TEXT;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
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
        return viewFactory.get(state).buildView(chatId, cached);
    }

    private State interceptSufficientInput(Update update) throws InsufficientInputException {
        log.info("Intercept sufficient input by user with id:{}", telegramUserId);
        SessionData cached = SessionDataCache.get(telegramUserId);
        if (cached == null) throw new InsufficientInputException();
        State currentState = cached.getStateHistory().peek();
        if (currentState == SEATS || currentState == COMMENT) {
            String text = update.getMessage().getText();
            cached.getCallbackHistory().push(text);
            HandlerType handlerType = handlerFactory.get(currentState);
            return handlerType.handle(cached);
        } else throw new WrongInputException();
    }
}
