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
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.view.View;
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
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getMessage();
        User from = message.getFrom();
        chatId = message.getChatId();
        telegramUserId = from.getId();
        State state;
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
        View<? extends BotApiMethod<?>> view = viewFactory.get(state);
        return view.buildView(chatId, cached);
    }

    State interceptTextCommand(String command, String username, Long telegramUserId) throws TextCommandNotFoundException {
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        try {
            var textCommand = TextCommand.valueOf(parsedAsCommand);
            return switch (textCommand) {
                case START -> {
                    UserDataCache.initializeByIdAndUsername(telegramUserId, username);
                    yield MENU;
                }
                default -> null;
            };
        } catch (IllegalArgumentException exception) {
            throw new TextCommandNotFoundException();
        }
    }

    private State interceptSufficientInput(Update update) throws InsufficientInputException {
        UserData cached = UserDataCache.get(telegramUserId);
        State currentState = cached.getStateHistory().peek();
        if (currentState == CARGO_PEOPLE || currentState == COMMENT) {
            String text = update.getMessage().getText();
            cached.getCallbackHistory().push(text);
            Handler handler = handlerFactory.get(currentState);
            return handler.handle(cached);
        } else throw new InsufficientInputException();
    }
}
