package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.impl.BackMenuKeyboard;
import com.godeltech.gbf.gui.keyboard.impl.PaginationKeyboard;
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
    private PaginationKeyboard paginationKeyboard;
    private BackMenuKeyboard backMenuKeyboard;
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramId = userData.getTelegramId();
        State currentState = userData.getStateHistory().peek();
        Page<TelegramUser> pages = switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> {
                if (currentState == REGISTRATIONS)
                    yield userService.findTelegramUsersByTelegramIdAndRole(
                            telegramId,
                            COURIER,
                            userData.getPageNumber());
                else yield null;
            }
            case REQUESTS_VIEWER -> {
                if (currentState == REQUESTS)
                    yield userService.findTelegramUsersByTelegramIdAndRole(
                            telegramId,
                            CLIENT,
                            userData.getPageNumber());
                else yield null;
            }
            case CLIENT -> null;
            default -> null;
        };
        userData.setPage(pages);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (pages != null && !pages.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(userData) :
                backMenuKeyboard.getKeyboardMarkup(userData);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).initialMessage(userData)).
                replyMarkup(keyboardMarkup).
                build());
        if (pages != null && !pages.isEmpty()) {
            for (TelegramUser telegramUser : pages) {
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
