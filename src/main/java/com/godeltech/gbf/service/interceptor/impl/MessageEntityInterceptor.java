package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.factory.impl.HandlerFactory;
import com.godeltech.gbf.factory.impl.ViewFactory;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.view.ViewType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static com.godeltech.gbf.model.State.MENU;

@Service
@Slf4j
public class MessageEntityInterceptor implements Interceptor {
    private final HandlerFactory handlerFactory;
    private final ViewFactory viewFactory;

    private final BotMessageService botMessageService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageEntityInterceptor(HandlerFactory handlerFactory, ViewFactory viewFactory, BotMessageService botMessageService) {
        this.handlerFactory = handlerFactory;
        this.viewFactory = viewFactory;
        this.botMessageService = botMessageService;
    }

    @Override
    public InterceptorTypes getType() {
        return InterceptorTypes.MESSAGE_ENTITY;
    }

    @Override
    public List<? extends BotApiMethod<?>> intercept(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();
        log.info("Get message user user : {} with id : {} ", user.getUserName(), user.getId());
        chatId = message.getChatId();
        telegramUserId = user.getId();
        botMessageService.save(telegramUserId, message);
        State state = interceptTextCommand(message.getText(), user.getUserName(), telegramUserId);
        UserData cached = UserDataCache.get(telegramUserId);
        cached.getStateHistory().push(state);
        cached.getCallbackHistory().push(update.getMessage().getText());
        return viewFactory.get(state).buildView(chatId, cached);
    }

    State interceptTextCommand(String command, String username, Long telegramUserId) throws TextCommandNotFoundException {
        log.info("Intercept text command : {} by user : {} with id : {}", command, username, telegramUserId);
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        switch (TextCommand.valueOf(parsedAsCommand)) {
            case START -> {
                UserDataCache.initializeByIdAndUsername(telegramUserId, username);
                return MENU;
            }
            default -> throw new TextCommandNotFoundException();
        }
    }
}
