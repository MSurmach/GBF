package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class RegistrationsStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;
    private Keyboard controlKeyboard;

    @Autowired
    public void setControlKeyboard(ControlKeyboard controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    @Autowired
    public void setBotRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationsStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long telegramId = callbackQuery.getFrom().getId();
        List<User> registrations = userRepository.findUsersByTelegramId(telegramId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setText(textAnswer(callbackQuery.getFrom().getUserName(), registrations));
        sendMessage.setReplyMarkup(controlKeyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(String username, List<User> registrations) {
        String mainMessage = registrations.isEmpty() ?
                localeMessageSource.getLocaleMessage("registrations.not_exist", username) :
                localeMessageSource.getLocaleMessage("registrations.exist", username);
        StringBuilder stringBuilder = new StringBuilder(mainMessage).append(System.lineSeparator());
        registrations.forEach(registration->stringBuilder.append(registration));
        return stringBuilder.toString();
    }
}
