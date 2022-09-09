package com.godeltech.gbf.bot_messages.impl;

import com.godeltech.gbf.bot_messages.BotMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartBotMessage implements BotMessage {

    @Override
    public SendMessage getMessageBotState() {
        SendMessage message = new SendMessage();
        String text = "Hello, please choose your role (you want to transfer something, or you want to look at list of people, that can help you). If you want to see your registrations, please click the \"Registrations\" button";
        message.setText(text);
        return null;
    }
}
