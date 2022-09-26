package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class CallbackInterceptor implements Interceptor {
    private StateHandlerFactory stateHandlerFactory;
    private StateView<SendMessage> stateView;

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Long userId = update.getCallbackQuery().getFrom().getId();
        Long chat_id = update.getCallbackQuery().getMessage().getChatId();
        UserData cached = UserDataCache.get(userId);
        String callback = update.getCallbackQuery().getData();
        State nextState;
        try {
            StateFlow stateFlow = StateFlow.valueOf(callback);
            nextState = stateFlow.getFirstState();
            cached.setStateFlow(stateFlow);
            cached.setCurrentState(nextState);
        } catch (IllegalArgumentException illegalArgumentException) {
            cached.setCallback(callback);
            switch (callback) {
                case "BACK" -> {
                    State previousState = cached.getPreviousState();
                    cached.setCurrentState(previousState);
                }
                case "MENU" -> {
                    cached.setCurrentState(State.MENU);
                }
                default -> {
                    State currentState = cached.getCurrentState();
                    stateHandlerFactory.get(currentState).handle(userId, cached);
                }
            }
            nextState = cached.getCurrentState();
        }
        return stateView.displayView(chat_id, cached);
    }
}
