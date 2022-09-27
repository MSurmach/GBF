package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.view.StateView;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class RegistrationsMainStateView implements StateView<SendMessage> {
    @Override
    public List<SendMessage> displayView(Long chatId, UserData userData) {
        return null;
    }
}
