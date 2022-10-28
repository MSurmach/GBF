package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.InsufficientInputException;
import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.exception.WrongInputException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.factory.impl.ViewFactory;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.view.ViewType;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static com.godeltech.gbf.model.State.*;

@Service
public class MessageInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final ViewFactory viewFactory;

    private final BotMessageService botMessageService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory, BotMessageService botMessageService) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
        this.botMessageService = botMessageService;
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getMessage();
        User from = message.getFrom();
        chatId = message.getChatId();
        telegramUserId = from.getId();
        State state;
        botMessageService.save(telegramUserId, message);
        try {
            state = interceptTextCommand(message.getText(), from.getUserName(), telegramUserId);
        } catch (TextCommandNotFoundException exception) {
            try {
                state = interceptSufficientInput(update);
            } catch (InsufficientInputException e) {
                throw new WrongInputException();
            }
        }
        UserData cached = UserDataCache.get(telegramUserId);
        cached.getStateHistory().push(state);
        cached.getCallbackHistory().push(update.getMessage().getText());
        ViewType<? extends BotApiMethod<?>> viewType = viewFactory.get(state);
        return viewType.buildView(chatId, cached);
    }

    State interceptTextCommand(String command, String username, Long telegramUserId) throws TextCommandNotFoundException {
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        try {
            TextCommand.valueOf(parsedAsCommand);
            UserDataCache.initializeByIdAndUsername(telegramUserId, username);
            return MENU;
        } catch (IllegalArgumentException exception) {
            throw new TextCommandNotFoundException();
        }
    }

    private State interceptSufficientInput(Update update) throws InsufficientInputException {
        UserData cached = UserDataCache.get(telegramUserId);
        if (cached == null) throw new InsufficientInputException();
        State currentState = cached.getStateHistory().peek();
        if (currentState == SEATS || currentState == COMMENT) {
            String text = update.getMessage().getText();
            cached.getCallbackHistory().push(text);
            HandlerType handlerType = handlerFactory.get(currentState);
            return handlerType.handle(cached);
        } else throw new InsufficientInputException();
    }
}
