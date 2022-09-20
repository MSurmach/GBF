package com.godeltech.gbf.controller;

import com.godeltech.gbf.LocaleMessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@RestController
public class BotController {

    private SpringWebhookBot springWebhookBot;
    private LocaleMessageSource localeMessageSource;

    public BotController(SpringWebhookBot springWebhookBot, LocaleMessageSource localeMessageSource) {
        this.springWebhookBot = springWebhookBot;
        this.localeMessageSource = localeMessageSource;
    }

    @PostMapping("/callback/gbf")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        /*String languageCode = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getFrom().getLanguageCode() :
                update.getMessage().getFrom().getLanguageCode();
        if (languageCode.equals("ru")) localeMessageSource.setLocale(languageCode);*/
        BotApiMethod<?> botApiMethod = springWebhookBot.onWebhookUpdateReceived(update);
        return botApiMethod;
    }
}
