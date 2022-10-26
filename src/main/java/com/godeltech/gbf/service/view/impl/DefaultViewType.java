package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.service.view.ViewType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.KeyboardType;
import com.godeltech.gbf.gui.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
public class DefaultViewType implements ViewType<SendMessage> {
    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private KeyboardFactory keyboardFactory;

    @Override
    public State getState() {
        return null;
    }

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        State state = userData.getStateHistory().peek();
        MessageType messageType = messageFactory.get(state);
        KeyboardType keyboardType = keyboardFactory.get(state);
        SendMessage sendMessage = SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(messageType.getMessage(userData)).
                replyMarkup(keyboardType.getKeyboardMarkup(userData)).build();
        return List.of(sendMessage);
    }
}
