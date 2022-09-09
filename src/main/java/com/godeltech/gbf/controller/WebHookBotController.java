package com.godeltech.gbf.controller;

import com.godeltech.gbf.service.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookBotController {

    private TelegramWebhookBot telegramWebhookBot;

    @Autowired
    public void setTelegramWebhookBot(TelegramWebhookBot telegramWebhookBot) {
        this.telegramWebhookBot = telegramWebhookBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        return telegramWebhookBot.onWebhookUpdateReceived(update);
    }
}
