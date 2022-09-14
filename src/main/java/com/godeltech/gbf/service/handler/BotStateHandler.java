package com.godeltech.gbf.service.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotStateHandler {
    void handleUpdate(Update update);
    SendMessage getView(Update update);
}
