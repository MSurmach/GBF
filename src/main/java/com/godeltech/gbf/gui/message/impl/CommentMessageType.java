package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.COMMENT_CODE;

@Component
@AllArgsConstructor
@Slf4j
public class CommentMessageType implements MessageType {

    private final LocalMessageSource localMessageSource;

    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        log.debug("Create comment message type for session data with user id : {} and username : {}",
                sessionData.getTelegramUserId(),sessionData.getUsername() );
        return localMessageSource.getLocaleMessage(COMMENT_CODE);
    }
}
