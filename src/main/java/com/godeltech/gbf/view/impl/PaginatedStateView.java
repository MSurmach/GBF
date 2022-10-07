package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.factory.StateKeyboardFactory;
import com.godeltech.gbf.service.factory.StateTextFactory;
import com.godeltech.gbf.service.keyboard.impl.BackMenuKeyboard;
import com.godeltech.gbf.service.keyboard.impl.PaginationKeyboard;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.view.StateView;
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
public class PaginatedStateView implements StateView<SendMessage> {
    private UserService userService;
    private PaginationKeyboard paginationKeyboard;
    private BackMenuKeyboard backMenuKeyboard;
    private StateTextFactory stateTextFactory;
    private StateKeyboardFactory stateKeyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramUserId = userData.getTelegramUserId();
        State currentState = userData.getStateHistory().peek();
        Page<UserRecord> records = switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> {
                if (currentState == REGISTRATIONS)
                    yield userService.findByTelegramUserIdAndRole(
                            telegramUserId,
                            COURIER,
                            userData.getPageNumber());
                else yield userData.getRecordsPage();
            }
            case REQUESTS_VIEWER -> {
                if (currentState == REQUESTS)
                    yield userService.findByTelegramUserIdAndRole(
                            telegramUserId,
                            CLIENT,
                            userData.getPageNumber());
                else yield userData.getRecordsPage();
            }
            case CLIENT -> userService.findCourierByUserDataAndRole(
                    userData,
                    COURIER,
                    userData.getPageNumber());
            default -> null;
        };
        userData.setRecordsPage(records);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (records != null && !records.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(userData) :
                backMenuKeyboard.getKeyboardMarkup(userData);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(stateTextFactory.get(currentState).initialMessage(userData)).
                replyMarkup(keyboardMarkup).
                build());
        if (records != null && !records.isEmpty()) {
            for (UserRecord record : records) {
                UserData dataFromRecord = new UserData(record);
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(stateTextFactory.get(currentState).getText(dataFromRecord)).
                        replyMarkup(stateKeyboardFactory.get(currentState).getKeyboardMarkup(dataFromRecord)).
                        build();
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
