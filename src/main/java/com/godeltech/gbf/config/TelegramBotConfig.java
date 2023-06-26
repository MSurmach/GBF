package com.godeltech.gbf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramBotConfig {
    @Value("${telegram.bot.username}")
    private String botUserName;

//    @Value("${telegram.bot.webHookHost}")
//    private String webhookHost;
//
//    @Value("${telegram.bot.endpoint}")
//    private String botEndpoint;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.internalURL}")
    private String internalURL;
}
