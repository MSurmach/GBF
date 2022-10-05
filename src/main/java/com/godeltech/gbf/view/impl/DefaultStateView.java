package com.godeltech.gbf.view.impl;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.text.Text;
import com.godeltech.gbf.service.factory.StateTextFactory;
import com.godeltech.gbf.service.factory.StateKeyboardFactory;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.view.StateView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultStateView implements StateView<SendMessage> {
    private StateTextFactory stateTextFactory;
    private StateKeyboardFactory stateKeyboardFactory;

    @Override
    public List<SendMessage> buildView(Long chatId, UserData userData) {
        State state = userData.getStateHistory().peek();
        Text text = stateTextFactory.get(state);
        Keyboard keyboard = stateKeyboardFactory.get(state);
        SendMessage sendMessage = SendMessage.
                builder().
                chatId(chatId).
                parseMode("html").
                text(text.getText(userData)).
                replyMarkup(keyboard.getKeyboardMarkup(userData)).build();
        return List.of(sendMessage);
    }
}
