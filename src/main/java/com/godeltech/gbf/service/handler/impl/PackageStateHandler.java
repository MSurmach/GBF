package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.management.State;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.PackageKeyboard;
import org.springframework.stereotype.Service;

@Service
public class PackageStateHandler extends LocaleBotStateHandler {

    public PackageStateHandler(LocalMessageSource localMessageSource, PackageKeyboard keyboard, LocalAnswer localBotMessage) {
        super(localMessageSource, keyboard, localBotMessage);
    }

    @Override
    public String handle(Long userId, String callback, UserData userData) {
        userData.setPackageSize(callback);
        State currentState = userData.getCurrentState();
        userData.setPreviousState(currentState);
        userData.setCurrentState(State.CARGO_MENU);
        return userId.toString();
    }
}
