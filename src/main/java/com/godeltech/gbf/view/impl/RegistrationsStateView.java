package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.impl.BackMenuKeyboard;
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
    private RegistrationRecordText registrationRecordText;
    private RegistrationRecordKeyboard registrationRecordKeyboard;
    private BackMenuKeyboard backMenuKeyboard;
    private UserService userService;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramUserId = userData.getTelegramUserId();
        Page<UserRecord> records = userService.findByTelegramUserIdAndRole(telegramUserId, COURIER, userData.getPageNumber());
        userData.setRecordsPage(records);
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(registrationsMainText.getText(userData)).
                replyMarkup(backMenuKeyboard.getKeyboardMarkup(userData)).
                build());
        if (records != null && !records.isEmpty()) {
            for (UserRecord record : records) {
                UserData dataFromRecord = new UserData(record);
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(registrationRecordText.getText(dataFromRecord)).
                        replyMarkup(registrationRecordKeyboard.getKeyboardMarkup(dataFromRecord)).
                        build();
                views.add(sendMessage);
            }
        }
        return views;
    }
}
