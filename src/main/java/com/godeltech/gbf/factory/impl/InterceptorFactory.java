package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.impl.CallbackInterceptor;
import com.godeltech.gbf.service.interceptor.impl.MessageInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
@Slf4j
public class InterceptorFactory {

//    Исправить, добавить InterceptorType для необходимым бинов Interceptros enum ;
    private final BeanFactory beanFactory;

    public Interceptor getInterceptor(Update update) {
        if (update.hasCallbackQuery()) {
            log.info("Get callback by user : {}", update.getCallbackQuery().getFrom().getUserName());
            return beanFactory.getBean(CallbackInterceptor.class);
        } else{
//            add logs
            return beanFactory.getBean(MessageInterceptor.class);
        }
    }
}
