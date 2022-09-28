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
import static com.godeltech.gbf.management.State.REGISTRATIONS;

@Service
public class CallbackInterceptor implements Interceptor {
    private final StateHandlerFactory stateHandlerFactory;
    private final StateViewFactory stateViewFactory;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public CallbackInterceptor(StateHandlerFactory stateHandlerFactory, StateViewFactory stateViewFactory) {
        this.stateHandlerFactory = stateHandlerFactory;
        this.stateViewFactory = stateViewFactory;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        telegramUserId = update.getCallbackQuery().getFrom().getId();
        chatId = update.getCallbackQuery().getMessage().getChatId();
        UserData cached = UserDataCache.get(telegramUserId);
        String callback = update.getCallbackQuery().getData();
        try {
            StateFlow stateFlow = StateFlow.valueOf(callback);
            State nextState = stateFlow.getFirstState();
            cached.setStateFlow(stateFlow);
            cached.setCurrentState(nextState);
        } catch (IllegalArgumentException illegalArgumentException) {
            cached.setCallback(callback);
            State state = cached.getCurrentState();
            switch (callback) {
                case "BACK" -> {
                    State previousState = cached.getPreviousState();
                    cached.setCurrentState(previousState);
                }
                case "MENU" -> state = MENU;
                case "REGISTRATIONS" -> state = REGISTRATIONS;
            }
            stateHandlerFactory.get(state).handle(cached);
        }
        cached = UserDataCache.get(telegramUserId);
        StateView<? extends SendMessage> stateView = stateViewFactory.get(cached.getCurrentState());
        return stateView.buildView(chatId, cached);
    }
}
