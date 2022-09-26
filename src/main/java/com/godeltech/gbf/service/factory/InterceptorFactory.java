package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.service.interceptor.impl.CallbackInterceptor;
import com.godeltech.gbf.service.interceptor.impl.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class InterceptorFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public InterceptorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BotApiMethod<?> intercept(Update update) {
        if (update.hasCallbackQuery()) {
            return applicationContext.getBean(CallbackInterceptor.class).intercept(update);
        } else return applicationContext.getBean(MessageInterceptor.class).intercept(update);
    }
}
