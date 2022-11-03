package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.YEAR_CODE;

@Component
@Slf4j
public class YearMessageType implements MessageType {

    private LocalMessageSource localMessageSource;

    public YearMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.YEAR;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create year message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return localMessageSource.getLocaleMessage(YEAR_CODE);
    }
}
