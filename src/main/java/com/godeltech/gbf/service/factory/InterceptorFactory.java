package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.service.interceptor.impl.CallBackQueryInterceptor;
import com.godeltech.gbf.service.interceptor.impl.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class InterceptorFactory {

    private ApplicationContext applicationContext;

    @Autowired
    public InterceptorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BotApiMethod<?> intercept(Update update) {
        if (update.hasCallbackQuery()) {
            return applicationContext.getBean(CallBackQueryInterceptor.class).intercept(update);
        }
        if (update.hasMessage()) {
            MessageInterceptor messageInterceptor = new MessageInterceptor();
            return applicationContext.getBean(MessageInterceptor.class).intercept(update);
        }
        return null;
    }
}
