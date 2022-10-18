package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.controller.GbfBot;
import com.godeltech.gbf.exception.CachedUserDataNotFound;
import com.godeltech.gbf.exception.NotNavigationButtonException;
import com.godeltech.gbf.exception.NotPaginationButtonException;
import com.godeltech.gbf.exception.RoleNotFoundException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.factory.impl.ViewFactory;
import com.godeltech.gbf.gui.button.NavigationBotButton;
import com.godeltech.gbf.gui.button.PaginationButton;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.handler.Handler;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static com.godeltech.gbf.model.State.BACK;
import static com.godeltech.gbf.model.State.MENU;

@Service
public class CallbackInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final ViewFactory viewFactory;
    private final MessageInterceptor messageInterceptor;

    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public CallbackInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory, MessageInterceptor messageInterceptor) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
        this.messageInterceptor = messageInterceptor;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getCallbackQuery().getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        User from = callbackQuery.getFrom();
        telegramUserId = from.getId();
        chatId = callbackQuery.getMessage().getChatId();
        UserData cached = null;
        State nextState = null;
        try {
            cached = pullFromCache(telegramUserId);
            cached.setCallbackQueryId(callbackQuery.getId());
            cached.getCallbackHistory().push(callbackQuery.getData());
            nextState = handleUpdate(update);
        } catch (CachedUserDataNotFound e) {
            nextState = messageInterceptor.interceptTextCommand(TextCommand.START.getDescription(), from.getUserName(), telegramUserId);
            cached = UserDataCache.get(telegramUserId);
        }
        cached.getStateHistory().push(nextState);
        View<? extends BotApiMethod<?>> view = viewFactory.get(nextState);
        return view.buildView(chatId, cached);
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

    private UserData pullFromCache(Long telegramUserId) throws CachedUserDataNotFound {
        UserData cached = UserDataCache.get(telegramUserId);
        if (cached == null) throw new CachedUserDataNotFound();
        return cached;
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
                    Handler handler = handlerFactory.get(BACK);
                    yield handler.handle(userData);
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
            Page<TelegramUser> page = userData.getPage();
            switch (clickedPagination) {
                case PAGE_START -> userData.setPageNumber(0);
                case PAGE_PREVIOUS -> {
                    if (page.hasPrevious()) {
                        int previousPageNumber = userData.getPageNumber() - 1;
                        userData.setPageNumber(previousPageNumber);
                    }
                }
                case PAGE_NEXT -> {
                    if (page.hasNext()) {
                        int nextPageNumber = userData.getPageNumber() + 1;
                        userData.setPageNumber(nextPageNumber);
                    }
                }
                case PAGE_END -> {
                    int lastPageNumber = page.getTotalPages() - 1;
                    userData.setPageNumber(lastPageNumber);
                }
            }
            return userData.getStateHistory().peek();

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NotPaginationButtonException();
        }
    }
}
