package com.godeltech.gbf.config;

import com.godeltech.gbf.GbfBot;
import com.godeltech.gbf.service.LocaleMessageSource;
import com.godeltech.gbf.interceptors.ReplyInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

@Configuration
public class ApplicationConfig {

    @Bean
    public TelegramWebhookBot gbfBot(TelegramBotConfig telegramBotConfig, ReplyInterceptor replyInterceptor) {
        return new GbfBot(telegramBotConfig.getBotUserName(), telegramBotConfig.getBotToken(), telegramBotConfig.getBotPath(), replyInterceptor);
    }

    @Bean
    public LocaleMessageSource localeMessageSource() {
        return new LocaleMessageSource(messageSource());
    }

    private MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("classpath:messages_en_EN.properties");
        return messageSource;
    }
}
