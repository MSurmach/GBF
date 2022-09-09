package com.godeltech.gbf.service.interceptors;

import com.godeltech.gbf.SendMessageBuilder;
import com.godeltech.gbf.service.LocaleMessageSource;
import com.godeltech.gbf.service.keyboard.impl.YearKeyboard;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageInterceptor implements Interceptor {
    private final String startCommand = "/start";
    private final String stopCommand = "/stop";

    @Override
    public BotApiMethod<?> intercept(Update update, LocaleMessageSource localeMessageSource, SendMessageBuilder sendMessageBuilder) {
        Message message = update.getMessage();
        String input = message.getText();
        if (input.equalsIgnoreCase(startCommand)) {
            String text = localeMessageSource.getLocaleMessage("start.message");
            message.setText(text);
            message.setReplyMarkup(new YearKeyboard().getKeyBoardMarkup());
            return sendMessageBuilder.reply(message);
        }
        return null;
    }
}
