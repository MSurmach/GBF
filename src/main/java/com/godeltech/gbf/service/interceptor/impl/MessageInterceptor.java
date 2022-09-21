package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.TextCommand;
import com.godeltech.gbf.service.factory.BotStateHandlerFactory;
import com.godeltech.gbf.service.handler.BotStateHandler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageInterceptor implements Interceptor {

    private TextCommand textCommand;

    @Autowired
    private BotStateHandlerFactory botStateHandlerFactory;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        String input = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();
        String username = update.getMessage().getFrom().getUserName();
        try {
            TextCommand textCommand = TextCommand.valueOf(input.toUpperCase().replace("/", ""));
            return switch (textCommand) {
                case START -> {
                    BotStateHandler handler = botStateHandlerFactory.getHandler(State.MENU);
                    handler.handle(userId, username, null);
                    yield handler.getView(chatId, userId, null);
                }
                case STOP -> {
                    yield null;
                }
                default -> null;
            };
        } catch (IllegalArgumentException exception) {
            BotStateHandler handler = botStateHandlerFactory.getHandler(State.WRONG_INPUT);
            return handler.getView(chatId, userId, null);
        }
    }
}
