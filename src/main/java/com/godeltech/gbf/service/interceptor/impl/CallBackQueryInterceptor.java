package com.godeltech.gbf.service.interceptor.impl;

import com.godeltech.gbf.service.interceptor.Interceptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallBackQueryInterceptor implements Interceptor {

    @Override
    public BotApiMethod<?> intercept(Update update) {
        return null;

    }
}
