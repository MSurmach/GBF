package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.stateHandler.BotStateHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackQueryInterceptor implements Interceptor {
    private BotStateHandlerFactory botStateHandlerFactory;

    @Autowired
    public void setBotStateHandlerFactory(BotStateHandlerFactory botStateHandlerFactory) {
        this.botStateHandlerFactory = botStateHandlerFactory;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Long telegramUserId = update.getCallbackQuery().getFrom().getId();
        UserData userDataFromCache = UserDataCache.getUserDataFromCache(telegramUserId);
        BotState botState;
        try {
            BotStateFlow botStateFlow = BotStateFlow.valueOf(update.getCallbackQuery().getData());
            botState = botStateFlow.getFirstState();
            userDataFromCache.setBotStateFlow(botStateFlow);
            userDataFromCache.setBotState(botState);
        } catch (IllegalArgumentException illegalArgumentException) {
            BotState currentBotState = userDataFromCache.getBotState();
            botStateHandlerFactory.getHandler(currentBotState).handleUpdate(update);
            botState = userDataFromCache.getBotState();
        }
        return botStateHandlerFactory.getHandler(botState).getView(update);
    }
}
