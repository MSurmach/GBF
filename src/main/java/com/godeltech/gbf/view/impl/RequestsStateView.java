package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.impl.PaginationKeyboard;
import com.godeltech.gbf.service.keyboard.impl.RequestKeyboard;
import com.godeltech.gbf.service.text.impl.RequestsText;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.model.Role.CLIENT;

@Service
@AllArgsConstructor
public class RequestsStateView implements StateView<SendMessage> {
    private UserService userService;
    private RequestsText requestsText;
    private RequestKeyboard requestKeyboard;
    private PaginationKeyboard paginationKeyboard;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        Page<UserRecord> records = userService.findByTelegramUserIdAndRole(
                userData.getTelegramUserId(),
                CLIENT,
                userData.getPageNumber());
        userData.setRecordsPage(records);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (records != null && !records.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(userData) :
                null;
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(requestsText.initialMessage(userData)).
                replyMarkup(keyboardMarkup).
                build());
        if (records != null && !records.isEmpty()) {
            for (UserRecord record : records) {
                UserData dataFromRecord = new UserData(record);
                messages.add(SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(requestsText.getText(dataFromRecord)).
                        replyMarkup(requestKeyboard.getKeyboardMarkup(dataFromRecord)).
                        build());
            }
        }
        return messages;
    }
}
