package com.godeltech.gbf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {

    private TelegramWebhookBot telegramWebhookBot;

    /* @Autowired
     private LocaleMessageSource localeMessageSource;
 */
    @Autowired
    public void setTelegramWebhookBot(TelegramWebhookBot telegramWebhookBot) {
        this.telegramWebhookBot = telegramWebhookBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        /*String languageCode = update.getMessage().getFrom().getLanguageCode();
        localeMessageSource.setLocale(languageCode);*/
        return telegramWebhookBot.onWebhookUpdateReceived(update);
    }
}
