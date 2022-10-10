package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.NotNavigationButtonException;
import com.godeltech.gbf.exception.NotPaginationButtonException;
import com.godeltech.gbf.exception.RoleNotFoundException;
import com.godeltech.gbf.gui.button.NavigationBotButton;
import com.godeltech.gbf.gui.button.PaginationButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.factory.impl.ViewFactory;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.service.view.StateView;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.godeltech.gbf.model.State.MENU;

@Service
public class CallbackInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final ViewFactory viewFactory;
    private final UserService userService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public CallbackInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory, UserService userService) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
        this.userService = userService;
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
        StateView<? extends BotApiMethod<?>> stateView = viewFactory.get(nextState);
        return stateView.buildView(chatId, cached);
    }

    private State handleUpdate(Update update) {
        try {
            return interceptRole(update);
        } catch (RoleNotFoundException illegalArgumentException) {
            try {
                return interceptNavigationButton(update);
            } catch (NotNavigationButtonException notNavigationButtonException) {
                try {
                    return interceptPaginationButton(update);
                } catch (NotPaginationButtonException notPaginationButtonException) {
                    UserData cached = UserDataCache.get(telegramUserId);
                    State currentState = cached.getStateHistory().peek();
                    Handler handler = handlerFactory.get(currentState);
                    return handler.handle(cached);
                }
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
                case MENU -> {
                    Handler handler = handlerFactory.get(MENU);
                    yield handler.handle(userData);
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotNavigationButtonException();
        }
    }

    private State interceptPaginationButton(Update update) throws NotPaginationButtonException {
        String callback = update.getCallbackQuery().getData();
        UserData userData = UserDataCache.get(telegramUserId);
        try {
            PaginationButton clickedPagination = PaginationButton.valueOf(callback);
            Page<UserRecord> recordsPage = userData.getRecordsPage();
            switch (clickedPagination) {
                case START -> userData.setPageNumber(0);
                case PREVIOUS -> {
                    if (recordsPage.hasPrevious()) {
                        int previousPageNumber = userData.getPageNumber() - 1;
                        userData.setPageNumber(previousPageNumber);
                    }
                }
                case NEXT -> {
                    if (recordsPage.hasNext()) {
                        int nextPageNumber = userData.getPageNumber() + 1;
                        userData.setPageNumber(nextPageNumber);
                    }
                }
                case END -> {
                    int lastPageNumber = recordsPage.getTotalPages() - 1;
                    userData.setPageNumber(lastPageNumber);
                }
            }
            return userData.getStateHistory().peek();

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotPaginationButtonException();
        }
    }
}
