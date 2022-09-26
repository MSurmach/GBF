package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.stereotype.Service;

@Service
public class SuccessStateHandler extends LocaleBotStateHandler {

    public SuccessStateHandler(LocalMessageSource localMessageSource, ControlKeyboard keyboard, LocalAnswer localBotMessage) {
        super(localMessageSource, keyboard, localBotMessage);
    }
}
