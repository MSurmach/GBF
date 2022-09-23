package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.stereotype.Service;

import static com.godeltech.gbf.management.State.CARGO_MENU;

@Service
public class PeopleStateHandler extends LocaleBotStateHandler {
    public PeopleStateHandler(LocalMessageSource localMessageSource, ControlKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        userData.setCompanionCount(Integer.parseInt(callback));
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(CARGO_MENU);
        return userId.toString();
    }
}
