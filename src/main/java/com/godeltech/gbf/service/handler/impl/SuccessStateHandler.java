package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.StateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.stereotype.Service;

@Service
public class SuccessStateHandler implements StateHandler {

    @Override
    public void handle(Long userId, UserData userData) {

    }
}
