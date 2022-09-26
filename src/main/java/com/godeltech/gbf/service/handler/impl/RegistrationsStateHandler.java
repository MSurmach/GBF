package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationsStateHandler implements StateHandler {
    private UserRepository userRepository;

    @Override
    public void handle(Long userId, UserData userData) {

    }
}
