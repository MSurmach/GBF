package com.godeltech.gbf.interceptors;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Interceptor {
    BotApiMethod<?> intercept(Update update);
}
