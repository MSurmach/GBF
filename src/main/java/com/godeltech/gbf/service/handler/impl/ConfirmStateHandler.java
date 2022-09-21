package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.StateFlow;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ConfirmKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmStateHandler extends LocaleBotStateHandler {
    private UserRepository userRepository;

    public ConfirmStateHandler(LocaleMessageSource localeMessageSource, ConfirmKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        StateFlow stateFlow = userData.getStateFlow();
        if (stateFlow == StateFlow.COURIER) {
            User user = new User(userData);
            userRepository.save(user);
        }
        userData.setCurrentState(stateFlow.getNextState(userData.getCurrentState()));
        return callback;
    }
}
