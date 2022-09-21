package com.godeltech.gbf.service.handler;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotStateHandler {
    String handle(Long userId, String callback, UserData userData);

    SendMessage getView(Long chatId, Long userId, String callback);

    void rememberState(State currentState, UserData userData, String callback);
}
