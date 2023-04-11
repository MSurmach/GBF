package com.godeltech.gbf.controller;

import com.godeltech.gbf.authorization.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@RestController
@AllArgsConstructor
@Slf4j
public class BotController {

    private SpringWebhookBot springWebhookBot;
    private AuthorizationService authorizationService;

    @PostMapping("/callback/${telegram.bot.endpoint}")
    public void onWebhookUpdateReceived(@RequestBody Update update) {
        log.info("Get update") ;
        if (authorizationService.isValid(update))
            springWebhookBot.onWebhookUpdateReceived(update);
    }
}
