package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.User;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.keyboard.impl.ConfirmKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationStateHandler implements StateHandler {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(Long userId, UserData userData) {
        StateFlow stateFlow = userData.getStateFlow();
        if (stateFlow == StateFlow.COURIER) {
            User user = new User(userData);
            userRepository.save(user);
        }
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(stateFlow.getNextState(currentState));
    }
}
