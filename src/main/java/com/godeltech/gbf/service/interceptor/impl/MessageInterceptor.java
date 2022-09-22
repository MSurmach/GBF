package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.controls.Command;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.service.factory.BotStateHandlerFactory;
import com.godeltech.gbf.service.handler.BotStateHandler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageInterceptor implements Interceptor {
    private final BotStateHandlerFactory botStateHandlerFactory;

    public MessageInterceptor(BotStateHandlerFactory botStateHandlerFactory) {
        this.botStateHandlerFactory = botStateHandlerFactory;
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
            Command.Text text = Command.Text.valueOf(parsedAsCommand);
            return switch (text) {
                case START -> {
                    BotStateHandler handler = botStateHandlerFactory.getHandler(State.MENU);
                    handler.handle(userId, username, null);
                    yield handler.getView(chatId, userId, null);
                }
                case STOP -> null;
                case HELP -> null;
            };
        } catch (IllegalArgumentException exception) {
            BotStateHandler handler = botStateHandlerFactory.getHandler(State.WRONG_INPUT);
            return handler.getView(chatId, userId, null);
        }
    }
}
