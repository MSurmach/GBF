package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.NotNavigationButtonException;
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
        cached.setCallbackQueryId(callbackQuery.getId());
        cached.getCallbackHistory().push(callbackQuery.getData());
        State nextState = handleUpdate(update);
        cached.getStateHistory().push(nextState);
        StateView<? extends BotApiMethod<?>> stateView = stateViewFactory.get(nextState);
        return stateView.buildView(chatId, cached);
    }

    private State handleUpdate(Update update) {
        try {
            return interceptRole(update);
        } catch (RoleNotFoundException illegalArgumentException) {
            try {
                return interceptNavigationButton(update);
            } catch (NotNavigationButtonException exception) {
                UserData cached = UserDataCache.get(telegramUserId);
                State currentState = cached.getStateHistory().peek();
                StateHandler stateHandler = stateHandlerFactory.get(currentState);
                return stateHandler.handle(cached);
            }
        }
    }

    private State interceptRole(Update update) throws RoleNotFoundException {
        try {
            String callback = update.getCallbackQuery().getData();
            Role role = Role.valueOf(callback);
            UserData cached = UserDataCache.get(telegramUserId);
            cached.setRole(role);
            return role.getFirstState();
        } catch (IllegalArgumentException exception) {
            throw new RoleNotFoundException();
        }
    }

    private State interceptNavigationButton(Update update) throws NotNavigationButtonException {
        String callback = update.getCallbackQuery().getData();
        try {
            NavigationBotButton botButton = NavigationBotButton.valueOf(callback);
            UserData userData = UserDataCache.get(telegramUserId);
            return switch (botButton) {
                case GLOBAL_BACK -> {
                    userData.getCallbackHistory().removeFirst();
                    userData.getCallbackHistory().removeFirst();
                    userData.getStateHistory().removeFirst();
                    yield userData.getStateHistory().pop();
                }
                case LOCAL_BACK -> null;
                case MENU -> {
                    StateHandler stateHandler = stateHandlerFactory.get(MENU);
                    yield stateHandler.handle(userData);
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotNavigationButtonException();
        }
    }
}
