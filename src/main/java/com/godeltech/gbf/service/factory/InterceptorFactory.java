package com.godeltech.gbf.service.factory;

import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.impl.CallbackInterceptor;
import com.godeltech.gbf.service.interceptor.impl.MessageInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class InterceptorFactory {

    private final BeanFactory beanFactory;

    public Interceptor getInterceptor(Update update) {
        if (update.hasCallbackQuery()) {
            return beanFactory.getBean(CallbackInterceptor.class);
        } else return beanFactory.getBean(MessageInterceptor.class);
    }
}
