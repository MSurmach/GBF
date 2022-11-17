package com.godeltech.gbf.config;

import com.godeltech.gbf.GbfBot;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

import java.util.Locale;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(DefaultWebhook defaultWebhook) {
        try {
            return new TelegramBotsApi(DefaultBotSession.class, defaultWebhook);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can not register a bot: " + e);
        }
    }

    @Bean
    public DefaultWebhook defaultWebhook(TelegramBotConfig telegramBotConfig, GbfBot gbfBot) {
        DefaultWebhook defaultWebhook = new DefaultWebhook();
        defaultWebhook.setInternalUrl(telegramBotConfig.getInternalURL());
        defaultWebhook.registerWebhook(gbfBot);
        return defaultWebhook;
    }

    @Bean
    public SetWebhook setWebhookInstance(TelegramBotConfig telegramBotConfig) {
        return SetWebhook.builder().
                url(telegramBotConfig.getWebhookHost()).
                dropPendingUpdates(true).
                maxConnections(100).
                build();
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setGetUpdatesTimeout(100);
        return defaultBotOptions;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultLocale(Locale.UK);
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("message", "button", "country_city", "alerts", "delivery", "language");
        return resourceBundleMessageSource;
    }
}
