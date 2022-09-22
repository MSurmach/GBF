package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.controls.StateFlow;
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
        State nextState;
        String callBackData = update.getCallbackQuery().getData();
        try {
            StateFlow stateFlow = StateFlow.valueOf(callBackData);
            //UserDataCache.saveCallback(userId, callBackData);
            nextState = stateFlow.getFirstState();
            cachedUserData.setStateFlow(stateFlow);
            cachedUserData.setCurrentState(nextState);
        } catch (IllegalArgumentException illegalArgumentException) {
            switch (callBackData) {
                case "BACK" -> {
                    State previousState = cachedUserData.getPreviousState();
                    cachedUserData.setCurrentState(previousState);
                    //callBackData = UserDataCache.getPreviousCallBack(userId);
                }
                case "MENU" -> {
                    cachedUserData.setCurrentState(State.MENU);
                }
                default -> {
                    State currentState = cachedUserData.getCurrentState();
                    callBackData = botStateHandlerFactory.getHandler(currentState).handle(userId, callBackData, cachedUserData);
                    //UserDataCache.saveCallback(userId, callBackData);
                }
            }
            nextState = cachedUserData.getCurrentState();
        }
        return botStateHandlerFactory.getHandler(nextState).getView(chat_id, userId, callBackData);
    }
}
