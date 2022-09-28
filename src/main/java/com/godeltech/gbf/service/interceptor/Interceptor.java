package com.godeltech.gbf.service.interceptor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Interceptor {
    List<? extends BotApiMethod<?>> intercept(Update update);

    Long getTelegramUserId();

    Long getChatId();
}
