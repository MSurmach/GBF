package com.godeltech.gbf.config;

import com.godeltech.gbf.GbfBot;
import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.service.factory.InterceptorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public TelegramWebhookBot gbfBot(TelegramBotConfig telegramBotConfig, InterceptorFactory interceptorFactory) {
        return new GbfBot(telegramBotConfig.getBotUserName(), telegramBotConfig.getBotToken(), telegramBotConfig.getBotPath(), interceptorFactory);
    }

    @Bean
    public LocaleMessageSource localeMessageSource(MessageSource messageSource) {
        return new LocaleMessageSource(messageSource);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultLocale(Locale.UK);
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("content", "buttons");
        return resourceBundleMessageSource;
    }
}
