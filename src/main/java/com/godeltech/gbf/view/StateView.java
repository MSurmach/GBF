package com.godeltech.gbf.view;

import com.godeltech.gbf.model.UserData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public interface StateView<T extends BotApiMethod<?>> {
    List<T> displayView(Long chatId, UserData userData);
}
