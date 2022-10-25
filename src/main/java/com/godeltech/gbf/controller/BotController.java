package com.godeltech.gbf.controller;

import com.godeltech.gbf.autorization.UpdateAutorization;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@RestController
@AllArgsConstructor
public class BotController {

    private SpringWebhookBot springWebhookBot;
    private UpdateAutorization authorization;

    @PostMapping("/callback/${telegram.bot.endpoint}")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        if (authorization.isValid(update))
            springWebhookBot.onWebhookUpdateReceived(update);
        return null;
    }
}
