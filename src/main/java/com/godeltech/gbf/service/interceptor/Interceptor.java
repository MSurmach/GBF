package com.godeltech.gbf.service.interceptor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Interceptor {
    BotApiMethod<?> intercept(Update update);
}
