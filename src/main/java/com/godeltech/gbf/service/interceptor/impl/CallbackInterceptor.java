package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.factory.StateViewFactory;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.view.StateView;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.godeltech.gbf.management.State.MENU;
import static com.godeltech.gbf.management.State.REGISTRATIONS_MAIN;

@Service
public class CallbackInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;
    private final StateViewFactory stateViewFactory;
    @Getter
    private Long userId;
    @Getter
    private Long chatId;
    public CallbackInterceptor(StateHandlerFactory stateHandlerFactory, StateViewFactory stateViewFactory) {
        this.stateHandlerFactory = stateHandlerFactory;
        this.stateViewFactory = stateViewFactory;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        userId = update.getCallbackQuery().getFrom().getId();
        chatId = update.getCallbackQuery().getMessage().getChatId();
        UserData cached = UserDataCache.get(userId);
        String callback = update.getCallbackQuery().getData();
        try {
            StateFlow stateFlow = StateFlow.valueOf(callback);
            State firstState = stateFlow.getFirstState();
            cached.setStateFlow(stateFlow);
            cached.setCurrentState(firstState);
        } catch (IllegalArgumentException illegalArgumentException) {
            cached.setCallback(callback);
            switch (callback) {
                case "BACK" -> {
                    State previousState = cached.getPreviousState();
                    cached.setCurrentState(previousState);
                }
                case "MENU_BACK" -> cached.setCurrentState(MENU);
                case "REGISTRATIONS" -> {
                    State state = REGISTRATIONS_MAIN;
                    cached.setCurrentState(state);
                    stateHandlerFactory.get(state).handle(userId, cached);
                }
                default -> {
                    State currentState = cached.getCurrentState();
                    stateHandlerFactory.get(currentState).handle(userId, cached);
                }
            }
        }
        State currentState = cached.getCurrentState();
        StateView<? extends SendMessage> stateView = stateViewFactory.get(currentState);
        return stateView.buildView(chatId, cached);
    }
}
