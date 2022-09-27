package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.godeltech.gbf.management.State.REGISTRATIONS_MAIN;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserRepository userRepository;

    @Override
    public void handle(Long userId, UserData userData) {
        List<UserData> usersByTelegramId = userRepository.findUsersByTelegramId(userId);
        userData.setRegistrations(usersByTelegramId);
        userData.setCurrentState(REGISTRATIONS_MAIN);
    }
}
