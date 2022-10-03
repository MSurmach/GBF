package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.exception.InsufficientInputException;
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
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.godeltech.gbf.model.State.*;

@Service
public class MessageInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;
    private final StateViewFactory stateViewFactory;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageInterceptor(StateHandlerFactory stateHandlerFactory, StateViewFactory stateViewFactory) {
        this.stateHandlerFactory = stateHandlerFactory;
        this.stateViewFactory = stateViewFactory;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getMessage();
        chatId = message.getChatId();
        telegramUserId = message.getFrom().getId();
        State state;
        try {
            state = interceptTextCommand(update);
        } catch (TextCommandNotFoundException exception) {
            try {
                state = interceptSufficientInput(update);
            } catch (InsufficientInputException e) {
                state = WRONG_INPUT;
            }
        }
        StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(state);
        return stateView.buildView(chatId, UserDataCache.get(telegramUserId));
    }

    private State interceptTextCommand(Update update) throws TextCommandNotFoundException {
        String input = update.getMessage().getText();
        String parsedAsCommand = input.toUpperCase().replace("/", "");
        try {
            var textCommand = TextCommand.valueOf(parsedAsCommand);
            String username = update.getMessage().getFrom().getUserName();
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

    private State interceptSufficientInput (Update update) throws InsufficientInputException {
        UserData cached = UserDataCache.get(telegramUserId);
        State currentState = cached.getCurrentState();
        if (currentState==CARGO_PEOPLE || currentState== COMMENT) {
            String text = update.getMessage().getText();
            cached.getCallbackHistory().add(text);
            StateHandler stateHandler = stateHandlerFactory.get(currentState);
            return stateHandler.handle(cached);
        }
        else throw new InsufficientInputException();
    }
}
