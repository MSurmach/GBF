package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.view.View;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FormView implements View<SendMessage> {
    private final MessageFactory messageFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        State currentState = userData.getStateHistory().peek();
        List<SendMessage> messages = new ArrayList<>();
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).initialMessage(userData)).
                replyMarkup(null).
                build());
        messages.add(SendMessage.builder().
                chatId(chatId).
                parseMode("html").
                text(messageFactory.get(currentState).getMessage(userData)).
                replyMarkup(keyboardFactory.get(currentState).getKeyboardMarkup(userData)).
                build());
        return messages;
    }
}
