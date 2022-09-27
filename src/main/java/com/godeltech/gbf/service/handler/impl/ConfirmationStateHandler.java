package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.StateHandler;
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
        try {
            userRepository.save(userData);
            State state = userData.getCurrentState();
            StateFlow stateFlow = userData.getStateFlow();
            userData.setCurrentState(stateFlow.getNextState(state));
        } catch (Exception exception) {

        }
    }
}
