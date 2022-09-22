package com.godeltech.gbf.service.handler.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.service.answer.LocalAnswerService;
import com.godeltech.gbf.service.handler.LocaleBotStateHandler;
import com.godeltech.gbf.service.keyboard.impl.ControlKeyboard;
import org.springframework.stereotype.Service;

@Service
public class WrongInputStateHandler extends LocaleBotStateHandler {

    public WrongInputStateHandler(LocalMessageSource localMessageSource, ControlKeyboard keyboard, LocalAnswerService localAnswerService) {
        super(localMessageSource, keyboard, localAnswerService);
    }
}
