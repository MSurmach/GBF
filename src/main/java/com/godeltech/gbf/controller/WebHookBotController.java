package com.godeltech.gbf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@RestController
public class WebHookBotController {

    private SpringWebhookBot springWebhookBot;

    @Autowired
    public void setSpringWebhookBot(SpringWebhookBot springWebhookBot) {
        this.springWebhookBot = springWebhookBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        return springWebhookBot.onWebhookUpdateReceived(update);
    }
}
