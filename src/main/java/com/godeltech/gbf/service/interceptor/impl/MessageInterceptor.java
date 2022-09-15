package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.Command;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.handler.BotStateHandler;
import com.godeltech.gbf.service.factory.BotStateHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageInterceptor implements Interceptor {

    private Command command;

    @Autowired
    private BotStateHandlerFactory botStateHandlerFactory;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        String input = update.getMessage().getText();
        try {
            Command command = Command.valueOf(input.toUpperCase().replace("/",""));
            return switch (command) {
                case START -> {
                    BotStateHandler handler = botStateHandlerFactory.getHandler(BotState.MENU);
                    handler.handleUpdate(update);
                    yield handler.getView(update);
                }
                case STOP -> {
                    yield null;
                }
            };
        } catch (IllegalArgumentException exception) {
            BotStateHandler handler = botStateHandlerFactory.getHandler(BotState.WRONG_INPUT);
            return handler.getView(update);
        }
    }
}
