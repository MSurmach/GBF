package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.user.TelegramUserService;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

import static com.godeltech.gbf.model.State.MENU;

@Service
@Slf4j
public class MessageEntityInterceptor implements Interceptor {
    private final View<? extends BotApiMethod<?>> view;
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageEntityInterceptor(View<SendMessage> view, BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.view = view;
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE_ENTITY;
    }

    @Override
    public BotApiMethod<?> intercept(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();
        log.info("Get message user user : {} with id : {} ", user.getUserName(), user.getId());
        chatId = message.getChatId();
        telegramUserId = user.getId();
        botMessageService.save(telegramUserId, message);
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findById(telegramUserId);
        State state = interceptTextCommand(message.getText(), optionalTelegramUser, user);
        SessionData cached = SessionDataCache.get(telegramUserId);
        cached.getStateHistory().push(state);
        cached.getCallbackHistory().push(update.getMessage().getText());
        return view.buildView(chatId, cached);
    }

    State interceptTextCommand(String command, Optional<TelegramUser> optionalTelegramUser, User updateFrom) throws TextCommandNotFoundException {
        log.info("Intercept text command : {} by user : {} with id : {}", command, updateFrom.getUserName(), updateFrom.getId());
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        switch (TextCommand.valueOf(parsedAsCommand)) {
            case START -> {
                optionalTelegramUser.ifPresentOrElse(SessionDataCache::initializeSession,
                        () -> {
                            TelegramUser createdUser = telegramUserService.saveUser(updateFrom.getId(), updateFrom.getUserName(), "ru");
                            SessionDataCache.initializeSession(createdUser);
                        });
                return MENU;
            }
            default -> throw new TextCommandNotFoundException();
        }
    }
}
