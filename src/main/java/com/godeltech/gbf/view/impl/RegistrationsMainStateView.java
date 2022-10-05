package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.answer.impl.RegistrationRecordAnswer;
import com.godeltech.gbf.service.answer.impl.RegistrationsMainAnswer;
import com.godeltech.gbf.service.keyboard.impl.BackMenuKeyboard;
import com.godeltech.gbf.service.keyboard.impl.RegistrationRecordKeyboard;
import com.godeltech.gbf.service.user.UserDataService;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static com.godeltech.gbf.model.Role.REGISTRATIONS_VIEWER;

@Service
@AllArgsConstructor
public class RegistrationsMainStateView implements StateView<SendMessage> {
    private RegistrationsMainAnswer registrationsMainAnswer;
    private RegistrationRecordAnswer registrationRecordAnswer;
    private RegistrationRecordKeyboard registrationRecordKeyboard;
    private BackMenuKeyboard backMenuKeyboard;

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
                text(registrationsMainAnswer.getAnswer(userData)).
                replyMarkup(backMenuKeyboard.getKeyboardMarkup(userData)).
                build());
        if (registrations != null && !registrations.isEmpty()) {
            for (UserData registration : registrations) {
                SendMessage sendMessage = SendMessage.builder().
                        chatId(chatId).
                        parseMode("html").
                        text(registrationRecordAnswer.getAnswer(registration)).
                        replyMarkup(registrationRecordKeyboard.getKeyboardMarkup(registration)).
                        build();
                views.add(sendMessage);
            }
        }
        return views;
    }
}
