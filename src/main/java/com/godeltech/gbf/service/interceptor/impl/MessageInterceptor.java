package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.button.BotButton;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.godeltech.gbf.management.State.*;

@Service
public class MessageInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;

    public MessageInterceptor(StateHandlerFactory stateHandlerFactory) {
        this.stateHandlerFactory = stateHandlerFactory;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        String input = message.getText();
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        String username = message.getFrom().getUserName();
        try {
            String parsedAsCommand = input.toUpperCase().replace("/", "");
            BotButton.Text text = BotButton.Text.valueOf(parsedAsCommand);
            return switch (text) {
                case START -> {
                    StateHandler handler = stateHandlerFactory.getHandler(MENU);
                    handler.handle(userId, username, null);
                    yield handler.getView(chatId, userId, null);
                }
                case STOP -> null;
                case HELP -> null;
            };
        } catch (IllegalArgumentException exception) {
            StateHandler handler;
            UserData cached = UserDataCache.get(userId);
            State currentState = cached.getCurrentState();
            try {
                Integer.parseInt(input);
                if (currentState != CARGO_PEOPLE) throw new IllegalArgumentException();
                handler = stateHandlerFactory.getHandler(currentState);
                handler.handle(userId, input, cached);
            } catch (IllegalArgumentException ex) {
                handler = stateHandlerFactory.getHandler(WRONG_INPUT);
                handler.handle(userId, input, cached);
            }
            State nextState = cached.getCurrentState();
            return stateHandlerFactory.getHandler(nextState).getView(chatId, userId, userId.toString());
        }
    }
}
