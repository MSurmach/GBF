package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.Command;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import com.godeltech.gbf.service.stateHandler.BotStateHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageInterceptor implements Interceptor {

    private Command command;

    @Autowired
    private BotStateHandlerFactory botStateHandlerFactory;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        String input = update.getMessage().getText();
        if (input.equalsIgnoreCase(Command.START.getText())) {
            BotState init = BotState.INIT;
            BotStateHandler handler = botStateHandlerFactory.getHandler(init);
            return handler.getView(update);
        }
        if (input.equalsIgnoreCase(Command.STOP.getText())) {
//            BotState finish = BotState.FINISH;
//            return botStateHandlerFactory.getHandler(finish).handleUpdate(update);
            return null;
        }
        return null;
    }
}
