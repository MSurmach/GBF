package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.CachedUserDataNotFound;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.gui.button.NavigationBotButton;
import com.godeltech.gbf.gui.button.PaginationButton;
import com.godeltech.gbf.model.ModelUtils;
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
    private final BotMessageService botMessageService;
    private final View<? extends BotApiMethod<?>> view;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public CallbackInterceptor(HandlerFactory handlerFactory, View<? extends BotApiMethod<?>> view, BotMessageService botMessageService) {
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
        Message message = update.getCallbackQuery().getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        User from = callbackQuery.getFrom();
        log.info("Got callback from user : {}", from.getUserName());
        telegramUserId = from.getId();
        chatId = callbackQuery.getMessage().getChatId();
        botMessageService.checkBotMessage(message.getMessageId(), telegramUserId, chatId);
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
            cached = SessionDataCache.get(telegramUserId);
        }
        cached.getStateHistory().push(nextState);
        return view.buildView(chatId, cached);
    }

    private State handleUpdate(Update update) {
        log.info("Handle update with callback data : {}", update.getCallbackQuery().getData());
            return interceptNavigationButton(update);
    }

    private SessionData pullFromCache(Long telegramUserId) throws CachedUserDataNotFound {
        log.info("Pull from cache with user id : {}", telegramUserId);
        SessionData cached = SessionDataCache.get(telegramUserId);
        if (cached == null) throw new CachedUserDataNotFound();
        return cached;
    }

    private State interceptNavigationButton(Update update){
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
                    ModelUtils.resetSessionData(sessionData);
                    yield MENU;
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            log.info(illegalArgumentException.getMessage() +" It's turn for intercepting Pagination Button");
            return interceptPaginationButton(update);
        }
    }

    private State interceptPaginationButton(Update update){
        String callback = update.getCallbackQuery().getData();
        SessionData sessionData = SessionDataCache.get(telegramUserId);
        try {
            log.info("Intercept pagination button with callback : {}", callback);
            PaginationButton clickedButton = PaginationButton.valueOf(callback);
            Page<Offer> page = sessionData.getOffers();
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
            log.info(illegalArgumentException.getMessage());
            SessionData cached = SessionDataCache.get(telegramUserId);
            State currentState = cached.getStateHistory().peek();
            HandlerType handlerType = handlerFactory.get(currentState);
            return handlerType.handle(cached);
        }
    }
}
