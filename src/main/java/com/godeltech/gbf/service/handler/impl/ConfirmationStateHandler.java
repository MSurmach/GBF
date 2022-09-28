package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.management.State;
import com.godeltech.gbf.management.StateFlow;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationStateHandler implements StateHandler {
    private UserDataRepository userDataRepository;

    @Autowired
    public void setUserRepository(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public void handle(UserData userData) {
        try {
            userDataRepository.save(userData);
            State state = userData.getCurrentState();
            StateFlow stateFlow = userData.getStateFlow();
            userData.setCurrentState(stateFlow.getNextState(state));
        } catch (Exception exception) {

        }
    }
}
