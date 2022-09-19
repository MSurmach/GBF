package com.godeltech.gbf.config;

import com.godeltech.gbf.GbfBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Configuration
public class RegistrationBotConfiguration {

    @Value("${telegram.bot.internalURL}")
    private String internalURL;
    @Autowired
    private GbfBot gbfBot;
    @Autowired
    private SetWebhook setWebhook;

    @Bean
    public DefaultWebhook defaultWebhook() {
        DefaultWebhook defaultWebhook = new DefaultWebhook();
        defaultWebhook.setInternalUrl(internalURL);
        return defaultWebhook;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhook());
            telegramBotsApi.registerBot(gbfBot, setWebhook);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can not register a bot: " + e);
        }
    }
}
