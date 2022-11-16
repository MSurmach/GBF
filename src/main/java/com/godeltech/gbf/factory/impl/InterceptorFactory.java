package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.service.interceptor.Interceptor;
import com.godeltech.gbf.service.interceptor.InterceptorTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterceptorFactory implements Factory<Interceptor, Update> {

    private final Map<InterceptorTypes, Interceptor> interceptorContext;

    public InterceptorFactory(List<Interceptor> interceptors) {
        this.interceptorContext = interceptors.stream()
                .collect(Collectors.toMap(Interceptor::getType, Function.identity()));
    }

    public Interceptor get(Update update) {
        if (update.hasCallbackQuery()) {
            log.info("Get callback interceptor by user : {}", update.getCallbackQuery().getFrom().getUserName());
            return interceptorContext.get(InterceptorTypes.CALLBACK);
        }
        log.info("Get message interceptors by user : {}", update.getMessage().getFrom().getUserName());
        return update.getMessage().hasEntities() ?
                interceptorContext.get(InterceptorTypes.MESSAGE_ENTITY) :
                interceptorContext.get(InterceptorTypes.MESSAGE_TEXT);
    }
}
