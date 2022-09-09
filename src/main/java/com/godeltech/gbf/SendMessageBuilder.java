package com.godeltech.gbf;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class SendMessageBuilder {
    private Long chat_id;
    private String text;
    private ReplyKeyboard keyboardMarkup;

    public SendMessage reply(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getText());
        sendMessage.setReplyMarkup(message.getReplyMarkup());
        return sendMessage;
    }

    public SendMessageBuilder keyBoardMarkup(ReplyKeyboard keyboardMarkup) {
        this.keyboardMarkup = keyboardMarkup;
        return this;
    }

    public SendMessageBuilder chat_id(Long chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendMessageBuilder text(String text) {
        this.text = text;
        return this;
    }

    public SendMessage build() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
