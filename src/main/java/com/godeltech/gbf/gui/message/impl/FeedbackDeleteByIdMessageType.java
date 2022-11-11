package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.DELETE_FEEDBACK_BY_ID_CODE;

@Component
@AllArgsConstructor
public class FeedbackDeleteByIdMessageType implements MessageType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FEEDBACK_DELETE_BY_ID;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(DELETE_FEEDBACK_BY_ID_CODE);
    }
}
