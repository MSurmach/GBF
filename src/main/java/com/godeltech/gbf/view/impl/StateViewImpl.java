package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.factory.StateAnswerFactory;
import com.godeltech.gbf.service.factory.StateKeyboardFactory;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.message.Answer;
import com.godeltech.gbf.view.StateView;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class StateViewImpl implements StateView<SendMessage> {
    private StateAnswerFactory stateAnswerFactory;
    private StateKeyboardFactory stateKeyboardFactory;

    @Override
    public SendMessage displayView(Long chatId, UserData userData) {
        State state = userData.getCurrentState();
        Answer answer = stateAnswerFactory.get(state);
        Keyboard keyboard = stateKeyboardFactory.get(state);
        return SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(answer.getBotMessage(userData)).
                replyMarkup(keyboard.getKeyboardMarkup(null)).
                build();
    }
}
