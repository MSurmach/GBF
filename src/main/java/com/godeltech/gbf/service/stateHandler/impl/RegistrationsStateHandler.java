package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RegistrationsStateHandler implements BotStateHandler {
    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        return null;
    }
}
