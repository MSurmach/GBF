package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.MONTH_CODE;

@Component
@Slf4j
public class MonthMessageType implements MessageType {
    private final LocalMessageSource localMessageSource;

    public MonthMessageType(LocalMessageSource localMessageSource) {
        this.localMessageSource = localMessageSource;
    }

    @Override
    public State getState() {
        return State.MONTH;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create month message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return localMessageSource.getLocaleMessage(MONTH_CODE);
    }
}
