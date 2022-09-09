package com.godeltech.gbf.service.interceptors;

import com.godeltech.gbf.SendMessageBuilder;
import com.godeltech.gbf.service.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class InterceptorFactory {

    private LocaleMessageSource localeMessageSource;
    private SendMessageBuilder sendMessageBuilder;

    @Autowired
    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }

    @Autowired
    public void setSendMessageBuilder(SendMessageBuilder sendMessageBuilder) {
        this.sendMessageBuilder = sendMessageBuilder;
    }

    public BotApiMethod<?> intercept(Update update) {
        if (update.hasCallbackQuery()) {
            CallBackQueryInterceptor callBackQueryInterceptor = new CallBackQueryInterceptor();
            return callBackQueryInterceptor.intercept(update, localeMessageSource, sendMessageBuilder);
        }
        if (update.hasMessage()) {
            MessageInterceptor messageInterceptor = new MessageInterceptor();
            return messageInterceptor.intercept(update, localeMessageSource, sendMessageBuilder);
        }
        return null;
    }
}
