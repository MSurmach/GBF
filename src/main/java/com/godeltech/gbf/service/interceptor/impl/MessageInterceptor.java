package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.command.TextCommand;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.factory.StateViewFactory;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.view.StateView;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.godeltech.gbf.model.State.MENU;
import static com.godeltech.gbf.model.State.WRONG_INPUT;

@Service
public class MessageInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;
    private final StateViewFactory stateViewFactory;

    public MessageInterceptor(StateHandlerFactory stateHandlerFactory, StateViewFactory stateViewFactory) {
        this.stateHandlerFactory = stateHandlerFactory;
        this.stateViewFactory = stateViewFactory;
    }

    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getMessage();
        String input = message.getText();
        chatId = message.getChatId();
        telegramUserId = message.getFrom().getId();
        String username = message.getFrom().getUserName();
        try {
            String parsedAsCommand = input.toUpperCase().replace("/", "");
            TextCommand text = TextCommand.valueOf(parsedAsCommand);
            return switch (text) {
                case START -> {
                    UserData created = UserDataCache.initializeByIdAndUsername(telegramUserId, username);
                    created.setCurrentState(MENU);
                    StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(MENU);
                    yield stateView.buildView(chatId, created);
                }
                case STOP -> null;
                case HELP -> null;
            };
        } catch (IllegalArgumentException exception) {
            StateHandler handler;
            UserData cached = UserDataCache.get(telegramUserId);
            State currentState = cached.getCurrentState();
            cached.setCallback(input);
            switch (currentState) {
                case CARGO_PEOPLE, COMMENT -> {
                    handler = stateHandlerFactory.get(currentState);
                    handler.handle(cached);
                }
                default -> {
                    handler = stateHandlerFactory.get(WRONG_INPUT);
                    handler.handle(cached);
                }
            }
            StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(cached.getCurrentState());
            return stateView.buildView(chatId, cached);
        }
    }
}
