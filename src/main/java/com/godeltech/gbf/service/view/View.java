package com.godeltech.gbf.service.view;

import com.godeltech.gbf.model.Session;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface View<T extends BotApiMethod<?>> {
    T buildView(Long chatId, Session session);
}
