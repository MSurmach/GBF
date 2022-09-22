package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.controls.State;
import com.godeltech.gbf.controls.StateFlow;
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

    public ConfirmStateHandler(LocalMessageSource localMessageSource, ConfirmKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
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
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(stateFlow.getNextState(currentState));
        return callback;
    }
}
