package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.SessionData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMessageType implements MessageType {
    public final static String COMMENT_CODE = "comment";
    private final LocalMessageSource localMessageSource;

    @Override
    public State getState() {
        return State.COMMENT;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return localMessageSource.getLocaleMessage(COMMENT_CODE);
    }
}
