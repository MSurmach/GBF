package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.impl.BackMenuKeyboard;
import com.godeltech.gbf.service.keyboard.impl.PaginationKeyboard;
import com.godeltech.gbf.service.keyboard.impl.RegistrationRecordKeyboard;
import com.godeltech.gbf.service.text.impl.RegistrationRecordText;
import com.godeltech.gbf.service.text.impl.RegistrationsMainText;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.model.Role.COURIER;

@Service
@AllArgsConstructor
public class RegistrationsStateView implements StateView<SendMessage> {
    private RegistrationsMainText registrationsMainText;
    private RegistrationRecordKeyboard registrationRecordKeyboard;
    private PaginationKeyboard paginationKeyboard;
    private BackMenuKeyboard backMenuKeyboard;
    private UserService userService;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramUserId = userData.getTelegramUserId();
        Page<UserRecord> records = userService.findByTelegramUserIdAndRole(
                telegramUserId,
                COURIER,
                userData.getPageNumber());
        userData.setRecordsPage(records);
        List<SendMessage> messages = new ArrayList<>();
        var keyboardMarkup = (records != null && !records.isEmpty()) ?
                paginationKeyboard.getKeyboardMarkup(userData) :
                backMenuKeyboard.getKeyboardMarkup(userData);
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(registrationsMainText.initialMessage(userData)).
                replyMarkup(keyboardMarkup).
                build());
        if (records != null && !records.isEmpty()) {
            for (UserRecord record : records) {
                UserData dataFromRecord = new UserData(record);
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(registrationsMainText.getText(dataFromRecord)).
                        replyMarkup(registrationRecordKeyboard.getKeyboardMarkup(dataFromRecord)).
                        build();
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
