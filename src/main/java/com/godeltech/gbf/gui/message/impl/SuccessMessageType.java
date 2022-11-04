package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.SUCCESS_CODE;

@Component
@Slf4j
public class SuccessMessageType implements MessageType {


    private final LocalMessageSource localMessageSource;

    public SuccessMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.SUCCESS_REGISTRATION;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create success message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return localMessageSource.getLocaleMessage(SUCCESS_CODE);
    }
}
