package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.BackMenuKeyboardType;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboardType;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.service.view.View;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.model.Role.CLIENT;
import static com.godeltech.gbf.model.Role.COURIER;
import static com.godeltech.gbf.model.State.REGISTRATIONS;
import static com.godeltech.gbf.model.State.REQUESTS;

@Service
@AllArgsConstructor
public class PaginatedView implements View<SendMessage> {
    private UserService userService;
    private PaginationKeyboardType paginationKeyboard;
    private BackMenuKeyboardType backMenuKeyboard;
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramId = userData.getTelegramId();
        State currentState = userData.getStateHistory().peek();
        Page<TelegramUser> page = switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> {
                if (currentState == REGISTRATIONS)
                    yield userService.findUsersByTelegramIdAndRole(telegramId, COURIER, userData.getPageNumber());
                else
                    yield userService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), CLIENT, userData.getPageNumber());
            }
            case REQUESTS_VIEWER -> {
                if (currentState == REQUESTS)
                    yield userService.findUsersByTelegramIdAndRole(telegramId, CLIENT, userData.getPageNumber());
                else
                    yield userService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), COURIER, userData.getPageNumber());
            }
            case CLIENT ->
                    userService.findTelegramUsersBySearchDataAndRole(userData.getTempForSearch(), COURIER, userData.getPageNumber());
            default -> null;
        };
        userData.setPage(page);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (page != null && !page.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(userData) :
                backMenuKeyboard.getKeyboardMarkup(userData);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).initialMessage(userData)).
                replyMarkup(keyboardMarkup).
                build());
        if (page != null && !page.isEmpty()) {
            for (TelegramUser telegramUser : page) {
                UserData fromDb = ModelUtils.createUserDataFromTelegramUser(telegramUser);
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
