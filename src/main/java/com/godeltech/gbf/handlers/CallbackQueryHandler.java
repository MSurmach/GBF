package com.godeltech.gbf.handlers;

import com.godeltech.gbf.BotState;
import com.godeltech.gbf.service.LocaleMessageSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackQueryHandler {
    private BotState botState;
    private LocaleMessageSource localeMessageSource;

    public BotApiMethod<?> handleUpdate(Update update) {
        return null;

    }
}
