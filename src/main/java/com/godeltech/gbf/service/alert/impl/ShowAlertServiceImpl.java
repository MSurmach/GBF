package com.godeltech.gbf.service.alert.impl;

import com.godeltech.gbf.GbfBot;
import com.godeltech.gbf.service.alert.ShowAlertService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
public class ShowAlertServiceImpl implements ShowAlertService {
    private GbfBot gbfBot;

    @Override
    public void showAlert(String callbackQueryId, String alertMessage) {
        AnswerCallbackQuery alert = AnswerCallbackQuery.builder().
                callbackQueryId(callbackQueryId).
                showAlert(true).
                text(alertMessage).
                cacheTime(60).
                build();
        try {
            gbfBot.execute(alert);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SendMessage makeSendMessage(Message message, String messageText) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(messageText)
                .build();
    }
}
