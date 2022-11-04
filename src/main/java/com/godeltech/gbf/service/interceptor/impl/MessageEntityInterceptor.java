package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.cache.SessionDataCache;
import com.godeltech.gbf.exception.TextCommandNotFoundException;
import com.godeltech.gbf.gui.command.TextCommand;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import com.godeltech.gbf.service.view.View;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.godeltech.gbf.model.State.MENU;

@Service
@Slf4j
public class MessageEntityInterceptor implements Interceptor {
    private final View<? extends BotApiMethod<?>> view;
    private final BotMessageService botMessageService;
    @Getter
    private Long telegramUserId;
    @Getter
    private Long chatId;

    public MessageEntityInterceptor(View<SendMessage> view, BotMessageService botMessageService) {
        this.view = view;
        this.botMessageService = botMessageService;
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
        State state = interceptTextCommand(message.getText(), user.getUserName(), user.getFirstName(), user.getLastName(), telegramUserId);
        SessionData cached = SessionDataCache.get(telegramUserId);
        cached.getStateHistory().push(state);
        cached.getCallbackHistory().push(update.getMessage().getText());
        return view.buildView(chatId, cached);
    }

    State interceptTextCommand(String command, String username, String firstName, String lastName, Long telegramUserId) throws TextCommandNotFoundException {
        log.info("Intercept text command : {} by user : {} with id : {}", command, username, telegramUserId);
        String parsedAsCommand = command.toUpperCase().replace("/", "");
        switch (TextCommand.valueOf(parsedAsCommand)) {
            case START -> {
                SessionDataCache.initializeByIdAndUsernameAndFirstNameAndLastName(telegramUserId, username, firstName, lastName);
                return MENU;
            }
            default -> throw new TextCommandNotFoundException();
        }
    }
}
