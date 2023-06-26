package com.godeltech.gbf.service.alert.impl;

import com.godeltech.gbf.service.GbfBot;
import com.godeltech.gbf.service.alert.ShowAlertService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
@Slf4j
public class ShowAlertServiceImpl implements ShowAlertService {
    @Override
    public AnswerCallbackQuery showAlert(String callbackQueryId, String alertMessage) {
        return AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(alertMessage).
                cacheTime(60).
                build();
    }

    @Override
    public SendMessage makeSendMessage(Message message, String messageText) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(messageText)
                .build();
    }
}
