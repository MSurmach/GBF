package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.factory.BotStateHandlerFactory;
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
        UserData userDataFromCache = UserDataCache.get(telegramUserId);
        BotState nextBotState;
        String callBackData = update.getCallbackQuery().getData();
        try {
            BotStateFlow botStateFlow = BotStateFlow.valueOf(callBackData);
            nextBotState = botStateFlow.getFirstState();
            userDataFromCache.setBotStateFlow(botStateFlow);
            userDataFromCache.setBotState(nextBotState);
        } catch (IllegalArgumentException illegalArgumentException) {
            switch (callBackData) {
                case "BACK" -> {
                    BotStateFlow currentBotFlow = userDataFromCache.getBotStateFlow();
                    BotState previousState = currentBotFlow.getPreviousState(userDataFromCache.getBotState());
                    userDataFromCache.setBotState(previousState);
                }
                case "MENU" -> {
                    userDataFromCache.setBotState(BotState.MENU);
                }
                default -> {
                    BotState currentBotState = userDataFromCache.getBotState();
                    botStateHandlerFactory.getHandler(currentBotState).handleUpdate(update);
                }
            }
            nextBotState = userDataFromCache.getBotState();
        }
        return botStateHandlerFactory.getHandler(nextBotState).getView(update);
    }
}
