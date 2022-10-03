package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.model.State.SUCCESS;

@Service
public class ConfirmationStateHandler implements StateHandler {
    private UserDataRepository userDataRepository;

    @Autowired
    public void setUserRepository(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public State handle(UserData userData) {
        userDataRepository.save(userData);
        Role role = userData.getRole();
        if (role == Role.COURIER)
            return SUCCESS;
        return userData.getCurrentState();
    }
}
