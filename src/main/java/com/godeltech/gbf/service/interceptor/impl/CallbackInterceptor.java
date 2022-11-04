package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.CachedUserDataNotFound;
import com.godeltech.gbf.exception.NotNavigationButtonException;
import com.godeltech.gbf.exception.NotPaginationButtonException;
import com.godeltech.gbf.exception.RoleNotFoundException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.gui.button.NavigationBotButton;
import com.godeltech.gbf.gui.button.PaginationButton;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.godeltech.gbf.model.State.BACK;
import static com.godeltech.gbf.model.State.MENU;
import static com.godeltech.gbf.service.interceptor.InterceptorTypes.CALLBACK;

@Service
@Slf4j
public class CallbackInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final View<? extends BotApiMethod<?>> view;
    private final BotMessageService botMessageService;

    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public CallbackInterceptor(HandlerFactory handlerFactory, View<SendMessage> view, BotMessageService botMessageService) {
        this.handlerFactory = handlerFactory;
        this.view = view;
        this.botMessageService = botMessageService;
    }

    @Override
    public InterceptorTypes getType() {
        return CALLBACK;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        botMessageService.checkBotMessage(update.getCallbackQuery().getMessage().getMessageId(),
                update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getChatId());
        Message message = update.getCallbackQuery().getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        User from = callbackQuery.getFrom();
        log.info("Got callback from user : {}", from.getUserName());

        telegramUserId = from.getId();
        chatId = callbackQuery.getMessage().getChatId();
        SessionData cached = null;
        State nextState = null;
        try {
            cached = pullFromCache(telegramUserId);
            cached.setCallbackQueryId(callbackQuery.getId());
            cached.getCallbackHistory().push(callbackQuery.getData());
            nextState = handleUpdate(update);
        } catch (CachedUserDataNotFound e) {
            log.info("Initialize new user");
            nextState = MENU;
            SessionDataCache.initializeByIdAndUsernameAndFirstNameAndLastName(telegramUserId, from.getUserName(), from.getFirstName(), from.getLastName());
//            nextState = messageTextInterceptor.interceptTextCommand(TextCommand.START.getDescription(), from.getUserName(), telegramUserId);
            cached = SessionDataCache.get(telegramUserId);
        }
        cached.getStateHistory().push(nextState);
        return view.buildView(chatId, cached);
    }

    private State handleUpdate(Update update) {
        log.info("Handle update with callback data : {}", update.getCallbackQuery().getData());
        try {
            return interceptRole(update);
        } catch (RoleNotFoundException illegalArgumentException) {
            try {
                return interceptNavigationButton(update);
            } catch (NotNavigationButtonException notNavigationButtonException) {
                try {
                    return interceptPaginationButton(update);
                } catch (NotPaginationButtonException notPaginationButtonException) {
                    SessionData cached = SessionDataCache.get(telegramUserId);
                    State currentState = cached.getStateHistory().peek();
                    HandlerType handlerType = handlerFactory.get(currentState);
                    return handlerType.handle(cached);
                }
            }
        }
    }

    private SessionData pullFromCache(Long telegramUserId) throws CachedUserDataNotFound {
        log.info("Pull from cache with user id : {}", telegramUserId);
        SessionData cached = SessionDataCache.get(telegramUserId);
        if (cached == null) throw new CachedUserDataNotFound();
        return cached;
    }

    private State interceptRole(Update update) throws RoleNotFoundException {
        try {
            String callback = update.getCallbackQuery().getData();
            log.info("Intercept role with callback : {}", callback);
            Role role = Role.valueOf(callback);
            SessionData cached = SessionDataCache.get(telegramUserId);
            cached.setRole(role);
            return role.getFirstState();
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            throw new RoleNotFoundException();
        }
    }

    private State interceptNavigationButton(Update update) throws NotNavigationButtonException {
        String callback = update.getCallbackQuery().getData();
        try {
            log.info("Intercept navigation buttons with callback : {}", callback);
            NavigationBotButton botButton = NavigationBotButton.valueOf(callback);

            SessionData sessionData = SessionDataCache.get(telegramUserId);
            return switch (botButton) {
                case GLOBAL_BACK -> {
                    HandlerType handlerType = handlerFactory.get(BACK);
                    yield handlerType.handle(sessionData);
                }
                case MENU -> {
                    HandlerType handlerType = handlerFactory.get(MENU);
                    yield handlerType.handle(sessionData);
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error(illegalArgumentException.getMessage());
            throw new NotNavigationButtonException();
        }
    }

    private State interceptPaginationButton(Update update) throws NotPaginationButtonException {
        String callback = update.getCallbackQuery().getData();
        SessionData sessionData = SessionDataCache.get(telegramUserId);
        try {
            log.info("Intercept pagination button with callback : {}", callback);
            PaginationButton clickedButton = PaginationButton.valueOf(callback);

            Page<Offer> page = sessionData.getPage();
            switch (clickedButton) {
                case PAGE_START -> sessionData.setPageNumber(0);
                case PAGE_PREVIOUS -> {
                    if (page.hasPrevious()) {
                        int previousPageNumber = sessionData.getPageNumber() - 1;
                        sessionData.setPageNumber(previousPageNumber);
                    }
                }
                case PAGE_NEXT -> {
                    if (page.hasNext()) {
                        int nextPageNumber = sessionData.getPageNumber() + 1;
                        sessionData.setPageNumber(nextPageNumber);
                    }
                }
                case PAGE_END -> {
                    int lastPageNumber = page.getTotalPages() - 1;
                    sessionData.setPageNumber(lastPageNumber);
                }
            }
            return sessionData.getStateHistory().peek();

        } catch (IllegalArgumentException illegalArgumentException) {
            log.error(illegalArgumentException.getMessage());
            throw new NotPaginationButtonException();
        }
    }
}
