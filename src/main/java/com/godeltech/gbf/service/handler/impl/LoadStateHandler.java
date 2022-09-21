package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocaleMessageSource;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.StateFlow;
import com.godeltech.gbf.model.Load;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.LoadKeyboard;
import org.springframework.stereotype.Service;

@Service
public class LoadStateHandler extends LocaleBotStateHandler {

    public LoadStateHandler(LocaleMessageSource localeMessageSource, LoadKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localeMessageSource, keyboard, localAnswerService);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        State currentState = userData.getCurrentState();
        userData.setLoad(Load.valueOf(callback));
        StateFlow stateFlow = userData.getStateFlow();
        userData.setCurrentState(stateFlow.getNextState(currentState));
        return callback;
    }
}
