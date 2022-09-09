package com.godeltech.gbf.service.interceptors;

import com.godeltech.gbf.SendMessageBuilder;
import com.godeltech.gbf.service.LocaleMessageSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Interceptor {
    BotApiMethod<?> intercept(Update update, LocaleMessageSource localeMessageSource, SendMessageBuilder sendMessageBuilder);
}
