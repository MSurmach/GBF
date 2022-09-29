package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.factory.StateViewFactory;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.view.StateView;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.godeltech.gbf.model.State.MENU;

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
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackQueryId = callbackQuery.getId();
        telegramUserId = callbackQuery.getFrom().getId();
        chatId = callbackQuery.getMessage().getChatId();
        String callback = callbackQuery.getData();
        UserData cached = UserDataCache.get(telegramUserId);
        try {
            Role role = Role.valueOf(callback);
            State firstState = role.getFirstState();
            cached.setRole(role);
            cached.setCurrentState(firstState);
        } catch (IllegalArgumentException illegalArgumentException) {
            cached.setCallback(callback);
            cached.setCallbackQueryId(callbackQueryId);
            State state = cached.getCurrentState();
            switch (callback) {
                case "BACK" -> {
                }
                case "MENU" -> state = MENU;
            }
            stateHandlerFactory.get(state).handle(cached);
        }
        cached = UserDataCache.get(telegramUserId);
        StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(cached.getCurrentState());
        return stateView.buildView(chatId, cached);
    }
}
