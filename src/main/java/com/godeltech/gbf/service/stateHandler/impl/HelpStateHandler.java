package com.godeltech.gbf.service.stateHandler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.Command;
import com.godeltech.gbf.service.stateHandler.BotStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Service
public class HelpStateHandler implements BotStateHandler {

    @Autowired
    private LocaleMessageSource localeMessageSource;

    @Override
    public SendMessage handleUpdate(Update update) {

        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        String userName = update.getMessage().getFrom().getUserName();
        sendMessage.setText(textAnswer(userName));
        return sendMessage;
    }

    private String textAnswer(String username) {
        String mainMessage = localeMessageSource.getLocaleMessage("help.message", username);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mainMessage).append(System.lineSeparator());
        Arrays.asList(Command.values()).forEach(command -> stringBuilder.append(command.getText()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }
}
