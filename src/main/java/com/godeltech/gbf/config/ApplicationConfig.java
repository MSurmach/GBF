package com.godeltech.gbf.config;

import com.godeltech.gbf.bot.GbfBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Configuration
public class ApplicationConfig {

    private TelegramBotConfig telegramBotConfig;

    @Autowired
    public void setTelegramBotConfig(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Bean
    public SetWebhook webhook() {
        return SetWebhook.builder().url(telegramBotConfig.getBotPath()).build();
    }

    @Bean
    public SpringWebhookBot gbfBot() {
        return new GbfBot(webhook(), telegramBotConfig.getBotUserName(), telegramBotConfig.getBotToken(), telegramBotConfig.getBotPath());
    }
}
