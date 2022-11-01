package com.godeltech.gbf.service.alert;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ExceptionResponseService {
    void showAlert(String callbackQueryId, String alertMessage);

    SendMessage makeSendMessage(Message message, String messageText);
}
