package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.Command;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Service
public class WrongInputStateHandler extends LocaleBotStateHandler {

    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public WrongInputStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    private String textAnswer(String username) {
        String mainMessage = localeMessageSource.getLocaleMessage("wrong_input", username);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mainMessage).append(System.lineSeparator());
        Arrays.asList(Command.values()).forEach(command -> stringBuilder.append(command.getText()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        String userName = update.getMessage().getFrom().getUserName();
        sendMessage.setText(textAnswer(userName));
        sendMessage.setReplyMarkup(controlKeyboard.getKeyboardMarkup());
        return sendMessage;
    }
}
