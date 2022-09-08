package com.godeltech.gbf.interceptors;

import com.godeltech.gbf.interceptors.impl.CallbackQueryInterceptor;
import com.godeltech.gbf.interceptors.impl.MessageInterceptor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class InterceptorFactory {

    private InterceptorFactory() {
    }

    private static InterceptorFactory interceptorFactory;

    public static InterceptorFactory getInstance() {
        return Objects.isNull(interceptorFactory) ? new InterceptorFactory() : interceptorFactory;
    }

    public Interceptor getInterceptor(Update update) {
        return update.hasCallbackQuery() ? new CallbackQueryInterceptor() : new MessageInterceptor();
    }
}
