package com.godeltech.gbf.service.view.impl;

import com.godeltech.gbf.service.view.View;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.factory.impl.KeyboardFactory;
import com.godeltech.gbf.factory.impl.MessageFactory;
import com.godeltech.gbf.gui.keyboard.Keyboard;
import com.godeltech.gbf.gui.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultView implements View<SendMessage> {
    private MessageFactory messageFactory;
    private KeyboardFactory keyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        State state = userData.getStateHistory().peek();
        Message message = messageFactory.get(state);
        Keyboard keyboard = keyboardFactory.get(state);
        SendMessage sendMessage = SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(message.getMessage(userData)).
                replyMarkup(keyboard.getKeyboardMarkup(userData)).build();
        return List.of(sendMessage);
    }
}
