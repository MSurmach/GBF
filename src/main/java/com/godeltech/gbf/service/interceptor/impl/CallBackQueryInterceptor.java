package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.BotState;
import com.godeltech.gbf.model.BotStateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.BotStateHandlerFactory;
import com.godeltech.gbf.service.interceptor.Interceptor;
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
        Long userId = update.getCallbackQuery().getFrom().getId();
        Long chat_id = update.getCallbackQuery().getMessage().getChatId();
        UserData cachedUserData = UserDataCache.get(userId);
        BotState nextBotState;
        String callBackData = update.getCallbackQuery().getData();
        try {
            BotStateFlow botStateFlow = BotStateFlow.valueOf(callBackData);
            nextBotState = botStateFlow.getFirstState();
            cachedUserData.setBotStateFlow(botStateFlow);
            cachedUserData.setCurrentBotState(nextBotState);
        } catch (IllegalArgumentException illegalArgumentException) {
            switch (callBackData) {
                case "BACK" -> {
                    BotStateFlow currentBotFlow = cachedUserData.getBotStateFlow();
                    BotState previousState = currentBotFlow.getPreviousState(cachedUserData.getCurrentBotState());
                    cachedUserData.setCurrentBotState(previousState);
                }
                case "MENU" -> {
                    cachedUserData.setCurrentBotState(BotState.MENU);
                }
                default -> {
                    BotState currentBotState = cachedUserData.getCurrentBotState();
                    botStateHandlerFactory.getHandler(currentBotState).handleUpdate(update);
                }
            }
            nextBotState = cachedUserData.getCurrentBotState();
        }
        return botStateHandlerFactory.getHandler(nextBotState).getView(chat_id, userId);
    }
}
