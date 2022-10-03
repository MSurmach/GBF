package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.ButtonNotFoundException;
import com.godeltech.gbf.exception.RoleNotFoundException;
import com.godeltech.gbf.management.button.NavigationBotButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateHandlerFactory;
import com.godeltech.gbf.service.factory.StateViewFactory;
import com.godeltech.gbf.service.handler.StateHandler;
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
        telegramUserId = callbackQuery.getFrom().getId();
        chatId = callbackQuery.getMessage().getChatId();
        UserData cached = UserDataCache.get(telegramUserId);
        State state;
        try {
            state = interceptRole(update, cached);
            cached.getStateHistory().push(state);
        } catch (RoleNotFoundException illegalArgumentException) {
            try {
                state = interceptNavigationButton(update);
            } catch (ButtonNotFoundException exception) {
                collectCallbackFromUpdate(update, cached);
                state = cached.getStateHistory().peek();
                StateHandler stateHandler = stateHandlerFactory.get(state);
                state = stateHandler.handle(cached);
                cached.getStateHistory().push(state);
            }
        }
        StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(state);
        return stateView.buildView(chatId, cached);
    }

    private State interceptRole(Update update, UserData userData) throws RoleNotFoundException {
        try {
            String callback = update.getCallbackQuery().getData();
            Role role = Role.valueOf(callback);
            userData.setRole(role);
            userData.getCallbackHistory().push(callback);
            return role.getFirstState();
        } catch (IllegalArgumentException exception) {
            throw new RoleNotFoundException();
        }
    }

    private void collectCallbackFromUpdate(Update update, UserData userData) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackQueryId = callbackQuery.getId();
        String callback = callbackQuery.getData();
        userData.getCallbackHistory().push(callback);
        userData.setCallbackQueryId(callbackQueryId);
    }

    private State interceptNavigationButton(Update update) throws ButtonNotFoundException {
        String callback = update.getCallbackQuery().getData();
        try {
            NavigationBotButton botButton = NavigationBotButton.valueOf(callback);
            UserData userData = UserDataCache.get(telegramUserId);
            return switch (botButton) {
                case GLOBAL_BACK -> {
                    userData.getCallbackHistory().removeFirst();
                    userData.getStateHistory().removeFirst();
                    yield userData.getStateHistory().peek();
                }
                case LOCAL_BACK -> null;
                case MENU -> {
                    StateHandler stateHandler = stateHandlerFactory.get(MENU);
                    yield stateHandler.handle(userData);
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ButtonNotFoundException();
        }
    }
}
