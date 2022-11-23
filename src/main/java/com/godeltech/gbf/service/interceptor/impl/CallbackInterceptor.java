package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.factory.impl.HandlerTypeFactory;
import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.button.NavigationBotButton;
import com.godeltech.gbf.gui.button.PaginationButton;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.handler.HandlerType;
import com.godeltech.gbf.service.interceptor.AbstractInterceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.view.View;
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
public class CallbackInterceptor extends AbstractInterceptor {

    public CallbackInterceptor(HandlerTypeFactory handlerTypeFactory, View<? extends BotApiMethod<?>> view, BotMessageService botMessageService, TelegramUserService telegramUserService, SessionCacheService sessionCacheService, LocalMessageSourceFactory localMessageSourceFactory) {
        super(handlerTypeFactory, botMessageService, telegramUserService, sessionCacheService, view, localMessageSourceFactory);
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
        Session session = connectSession(telegramUserId, from.getUserName());
        session.setCallbackQueryId(callbackQuery.getId());
        session.getCallbackHistory().push(callbackQuery.getData());
        State nextState = handleUpdate(update, session);
        session.getStateHistory().push(nextState);
        return view.buildView(chatId, session);
    }

    private State handleUpdate(Update update, Session session) {
        log.info("Handle update with callback data : {}", update.getCallbackQuery().getData());
        return interceptNavigationButton(update, session);
    }

    private State interceptNavigationButton(Update update, Session session) {
        String callback = update.getCallbackQuery().getData();
        try {
            log.info("Intercept navigation buttons with callback : {}", callback);
            NavigationBotButton botButton = NavigationBotButton.valueOf(callback);
            return switch (botButton) {
                case BACK -> {
                    HandlerType handlerType = handlerTypeFactory.get(BACK);
                    yield handlerType.handle(session);
                }
                case MENU -> {
                    ModelUtils.resetSessionData(session);
                    yield MENU;
                }
            };
        } catch (IllegalArgumentException illegalArgumentException) {
            log.info(illegalArgumentException.getMessage() + " It's turn for intercepting Pagination Button");
            return interceptPaginationButton(update, session);
        }
    }

    private State interceptPaginationButton(Update update, Session session) {
        String callback = update.getCallbackQuery().getData();
        try {
            log.info("Intercept pagination button with callback : {}", callback);
            PaginationButton clickedButton = PaginationButton.valueOf(callback);
            Page<Offer> page = session.getOffers();
            switch (clickedButton) {
                case PAGE_START -> session.setPageNumber(0);
                case PAGE_PREVIOUS -> {
                    if (page.hasPrevious()) {
                        int previousPageNumber = session.getPageNumber() - 1;
                        session.setPageNumber(previousPageNumber);
                    }
                }
                case PAGE_NEXT -> {
                    if (page.hasNext()) {
                        int nextPageNumber = session.getPageNumber() + 1;
                        session.setPageNumber(nextPageNumber);
                    }
                }
                case PAGE_END -> {
                    int lastPageNumber = page.getTotalPages() - 1;
                    session.setPageNumber(lastPageNumber);
                }
            }
            return session.getStateHistory().peek();

        } catch (IllegalArgumentException illegalArgumentException) {
            log.info(illegalArgumentException.getMessage());
            State currentState = session.getStateHistory().peek();
            HandlerType handlerType = handlerTypeFactory.get(currentState);
            return handlerType.handle(session);
        }
    }
}
