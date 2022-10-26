package com.godeltech.gbf.service.view;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public interface ViewType<T extends BotApiMethod<?>> {
    State getState();
    List<T> buildView(Long chatId, UserData userData);
}
