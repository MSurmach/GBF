package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.keyboard.impl.RegistrationRecordKeyboard;
import com.godeltech.gbf.service.text.impl.RegistrationRecordText;
import com.godeltech.gbf.service.text.impl.RegistrationsMainText;
import com.godeltech.gbf.service.user.UserDataService;
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

    private UserDataService userDataService;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        long telegramUserId = userData.getTelegramUserId();
        List<UserData> registrations = userDataService.findUserDataByTelegramUserId(telegramUserId);
        userData.setRegistrations(registrations);
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(registrationsMainText.getText(userData)).
                replyMarkup(null).
                build());
        if (registrations != null && !registrations.isEmpty()) {
            for (UserData registration : registrations) {
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(registrationRecordText.getText(registration)).
                        replyMarkup(registrationRecordKeyboard.getKeyboardMarkup(registration)).
                        build();
                views.add(sendMessage);
            }
        }
        return views;
    }
}
