package com.godeltech.gbf.service.view;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public interface ViewType<T extends BotApiMethod<?>> {
    State getState();
    List<T> buildView(Long chatId, SessionData sessionData);
}
