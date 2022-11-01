package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.SuccessRegistrationKeyboardType;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public abstract class PaginatedView  {
    private TelegramUserService telegramUserService;
    private PaginationKeyboardType paginationKeyboard;
    private SuccessRegistrationKeyboardType backMenuKeyboard;
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;


    public List<SendMessage> buildView(Long chatId, SessionData sessionData) {
        long telegramId = sessionData.getTelegramId();
        State currentState = sessionData.getStateHistory().peek();
        Page<TelegramUser> page = null;
//        switch (userData.getRole()) {
//            case REGISTRATIONS_VIEWER -> {
//                if (currentState == REGISTRATIONS)
//                    yield telegramUserService.findUsersByTelegramIdAndRole(telegramId, COURIER, userData.getPageNumber());
//                else
//                    yield telegramUserService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), CLIENT, userData.getPageNumber());
//            }
//            case REQUESTS_VIEWER -> {
//                if (currentState == REQUESTS)
//                    yield telegramUserService.findUsersByTelegramIdAndRole(telegramId, CLIENT, userData.getPageNumber());
//                else
//                    yield telegramUserService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), COURIER, userData.getPageNumber());
//            }
//            case CLIENT ->
//                    telegramUserService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), COURIER, userData.getPageNumber());
//            default -> null;
//        };
        sessionData.setPage(page);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (page != null && !page.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(sessionData) :
                backMenuKeyboard.getKeyboardMarkup(sessionData);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).initialMessage(sessionData)).
                replyMarkup(keyboardMarkup).
                build());
        if (page != null && !page.isEmpty()) {
            for (TelegramUser telegramUser : page) {
                SessionData fromDb = ModelUtils.createUserDataFromTelegramUser(telegramUser);
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(messageFactory.get(currentState).getMessage(fromDb)).
                        replyMarkup(keyboardFactory.get(currentState).getKeyboardMarkup(fromDb)).
                        build();
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
