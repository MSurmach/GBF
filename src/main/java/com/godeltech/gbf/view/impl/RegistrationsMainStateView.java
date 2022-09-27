package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.impl.RegistrationRecordAnswer;
import com.godeltech.gbf.service.answer.impl.RegistrationsMainAnswer;
import com.godeltech.gbf.service.keyboard.impl.RegistrationRecordKeyboard;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationsMainStateView implements StateView<SendMessage> {
    private RegistrationsMainAnswer registrationsMainAnswer;
    private RegistrationRecordAnswer registrationRecordAnswer;
    private RegistrationRecordKeyboard registrationRecordKeyboard;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        List<SendMessage> views = new ArrayList<>();
        views.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(registrationsMainAnswer.getAnswer(userData)).
                build());
        List<UserData> registrations = userData.getRegistrations();
        if (registrations != null && !registrations.isEmpty()) {
            registrations.forEach(registration ->
                    views.add(SendMessage.builder().
                            chatId(chatId).
                            parseMode("html").
                            text(registrationRecordAnswer.getAnswer(registration)).
                            replyMarkup(registrationRecordKeyboard.getKeyboardMarkup(registration)).
                            build()));
        }
        return views;
    }
}