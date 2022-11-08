package com.godeltech.gbf.service.view;

import com.godeltech.gbf.model.SessionData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface View<T extends BotApiMethod<?>> {
    T buildView(Long chatId, SessionData sessionData);
}
