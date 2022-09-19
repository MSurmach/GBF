package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.cache.UserDataCache;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class UsersListBotStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersListBotStateHandler(LocaleMessageSource localeMessageSource, ControlKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public void handleUpdate(Update update) {

    }

    @Override
    public SendMessage getView(Long chatId, Long userId) {
        UserData cachedUserData = UserDataCache.get(userId);
        List<User> users = userRepository.findUsersByCityFromAndCityTo(cachedUserData.getCityFrom(), cachedUserData.getCityTo());
        return createMessage(chatId, localAnswerService.getTextAnswer(cachedUserData), keyboard.getKeyboardMarkup());
    }
}
