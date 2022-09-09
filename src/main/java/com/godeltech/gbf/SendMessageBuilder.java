package com.godeltech.gbf;

import com.godeltech.gbf.keyboard.KeyboardFactory;
import com.godeltech.gbf.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class SendMessageBuilder {
    private Long chat_id;
    private String text;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private BotState botState;

    public SendMessageBuilder(BotState botState) {
        this.botState = botState;
    }

    public SendMessageBuilder chat_id(Long chat_id) {
        this.chat_id=chat_id;
        return this;
    }

    public SendMessageBuilder text(String text){
        this.text = text;
        return this;
    }

    public SendMessage build(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id);
        sendMessage.setText(text);

        InlineKeyboardMarkup keyBoardMarkup = KeyboardFactory.getKeyboardMarkupFactory().getKeyboard(botState).getKeyBoardMarkup();
        sendMessage.setReplyMarkup(keyBoardMarkup);
        return sendMessage;
    }
}
