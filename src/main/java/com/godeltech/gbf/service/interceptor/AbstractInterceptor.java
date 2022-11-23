package com.godeltech.gbf.service.interceptor;

import com.godeltech.gbf.factory.impl.HandlerTypeFactory;
import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.session_cache.SessionCacheService;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractInterceptor implements Interceptor {
    protected final HandlerTypeFactory handlerTypeFactory;
    protected final LocalMessageSourceFactory localMessageSourceFactory;
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final SessionCacheService sessionCacheService;
    protected final View<? extends BotApiMethod<?>> view;

    @Getter
    protected Long telegramUserId;
    @Getter
    protected Long chatId;

    protected AbstractInterceptor(HandlerTypeFactory handlerTypeFactory, BotMessageService botMessageService, TelegramUserService telegramUserService, SessionCacheService sessionCacheService, View<? extends BotApiMethod<?>> view, LocalMessageSourceFactory localMessageSourceFactory) {
        this.handlerTypeFactory = handlerTypeFactory;
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.sessionCacheService = sessionCacheService;
        this.view = view;
        this.localMessageSourceFactory = localMessageSourceFactory;
    }

    protected Session connectSession(Long telegramUserId, String username) {
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findById(telegramUserId);
        if (optionalTelegramUser.isPresent()) {
            TelegramUser telegramUser = optionalTelegramUser.get();
            if (!Objects.equals(telegramUser.getUserName(), username)) {
                telegramUser.setUserName(username);
                telegramUserService.saveUser(telegramUser);
            }
            return sessionCacheService.get(telegramUser);
        } else {
            TelegramUser createdUser = telegramUserService.saveUser(
                    TelegramUser.builder().
                            userName(username).
                            id(telegramUserId).
                            language(localMessageSourceFactory.getDefaultMessageSource().getLanguage()).build()
            );
            return sessionCacheService.get(createdUser);
        }
    }
}
