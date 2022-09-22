package com.godeltech.gbf.config;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controller.GbfBot;
import com.godeltech.gbf.service.factory.InterceptorFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import java.util.Locale;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
    private TelegramBotConfig telegramBotConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
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
    public LocalMessageSource localeMessageSource(MessageSource messageSource) {
        return new LocalMessageSource(messageSource);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultLocale(Locale.UK);
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("message", "buttons", "model");
        return resourceBundleMessageSource;
    }
}
