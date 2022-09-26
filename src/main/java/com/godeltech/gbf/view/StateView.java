package com.godeltech.gbf.view;

import com.godeltech.gbf.model.UserData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface StateView<T extends BotApiMethod<?>> {
    T displayView(Long chatId, UserData userData);
}
