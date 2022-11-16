package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.WRONG_INPUT_CODE;

@Component
@Slf4j
public class WrongInputTextMessageType implements TextMessageType {
    private final LocalMessageSource localMessageSource;

    public WrongInputTextMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.WRONG_INPUT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create wrong input message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return localMessageSource.getLocaleMessage(WRONG_INPUT_CODE, sessionData.getUsername());
    }
}
