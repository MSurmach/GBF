package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.command.TextCommand;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.godeltech.gbf.management.State.MENU;
import static com.godeltech.gbf.management.State.WRONG_INPUT;

@Service
@AllArgsConstructor
public class MessageInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;
    private final StateView<SendMessage> stateView;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        String input = message.getText();
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        String username = message.getFrom().getUserName();
        try {
            String parsedAsCommand = input.toUpperCase().replace("/", "");
            TextCommand text = TextCommand.valueOf(parsedAsCommand);
            return switch (text) {
                case START -> {
                    StateHandler handler = stateHandlerFactory.get(MENU);
                    UserData created = new UserData();
                    created.setUsername(username);
                    handler.handle(userId, created);
                    yield stateView.displayView(chatId, created);
                }
                case STOP -> null;
                case HELP -> null;
            };
        } catch (IllegalArgumentException exception) {
            StateHandler handler;
            UserData cached = UserDataCache.get(userId);
            State currentState = cached.getCurrentState();
            cached.setCallback(input);
            switch (currentState) {
                case CARGO_PEOPLE, COMMENT -> {
                    handler = stateHandlerFactory.get(currentState);
                    handler.handle(userId, cached);
                }
                default -> {
                    handler = stateHandlerFactory.get(WRONG_INPUT);
                    handler.handle(userId, cached);
                }
            }
            return stateView.displayView(chatId, cached);
        }
    }
}
