package com.godeltech.gbf.config;

import com.godeltech.gbf.controller.GbfBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Configuration
public class RegistrationBotConfiguration {

    @Value("${telegram.bot.internalURL}")
    private String internalURL;

    private final GbfBot gbfBot;

    public RegistrationBotConfiguration(GbfBot gbfBot) {
        this.gbfBot = gbfBot;
    }

    @Bean
    public DefaultWebhook defaultWebhook() {
        DefaultWebhook defaultWebhook = new DefaultWebhook();
        defaultWebhook.setInternalUrl(internalURL);
        defaultWebhook.registerWebhook(gbfBot);
        return defaultWebhook;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            return new TelegramBotsApi(DefaultBotSession.class, defaultWebhook());
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can not register a bot: " + e);
        }
    }
}
