package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
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

import java.util.List;

@Service
public class UsersListStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersListStateHandler(LocalMessageSource localMessageSource, ControlKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public SendMessage getView(Long chatId, Long userId, String callback) {
        UserData cachedUserData = UserDataCache.get(userId);
        List<User> users = userRepository.findUsersByCityFromAndCityTo(cachedUserData.getCityFrom(), cachedUserData.getCityTo());
        return createMessage(chatId, localAnswerService.getTextAnswer(cachedUserData), keyboard.getKeyboardMarkup(callback));
    }
}
