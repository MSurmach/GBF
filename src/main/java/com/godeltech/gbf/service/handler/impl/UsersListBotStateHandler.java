package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.Keyboard;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UsersListBotStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;

    private Keyboard keyboard;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setKeyboard(ControlKeyboard keyboard) {
        this.keyboard = keyboard;
    }


    public UsersListBotStateHandler(LocaleMessageSource localeMessageSource) {
        super(localeMessageSource);
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        UserData cachedUserData = UserDataCache.get(callbackQuery.getFrom().getId());
        List<User> users = userRepository.findUsersByCityFromAndCityTo(cachedUserData.getCityFrom(), cachedUserData.getCityTo());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setText(textAnswer(users));
        sendMessage.setReplyMarkup(keyboard.getKeyboardMarkup());
        return sendMessage;
    }

    private String textAnswer(List<User> users) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        StringBuilder stringBuilder = new StringBuilder();
        users.forEach(user -> {
            String record = localeMessageSource.getLocaleMessage("list.user.data",
                    user.getUsername(),
                    user.getCountryFrom(),
                    user.getCityFrom(),
                    user.getDateFrom().format(dateTimeFormatter),
                    user.getCountryTo(),
                    user.getCityTo(),
                    user.getDateTo().format(dateTimeFormatter),
                    user.getLoad().name());
            stringBuilder.append(record).append("\n\n");
        });
        return stringBuilder.toString();
    }
}
