package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.service.keyboard.impl.RegistrationRecordKeyboard;
import com.godeltech.gbf.service.text.impl.RegistrationRecordText;
import com.godeltech.gbf.service.text.impl.RegistrationsMainText;
import com.godeltech.gbf.service.user.UserService;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationsStateView implements StateView<SendMessage> {
    private RegistrationsMainText registrationsMainText;
    private RegistrationRecordText registrationRecordText;
    private RegistrationRecordKeyboard registrationRecordKeyboard;

    private UserService userService;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramUserId = userData.getTelegramUserId();
        List<UserRecord> records = userService.findByTelegramUserId(telegramUserId);
        userData.setRecords(records);
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(registrationsMainText.getText(userData)).
                replyMarkup(null).
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
