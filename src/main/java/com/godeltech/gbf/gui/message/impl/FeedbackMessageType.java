package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_ABOUT_CODE;
import static com.godeltech.gbf.gui.utils.ConstantUtil.FEEDBACK_QUESTION_CODE;

@Component
@AllArgsConstructor
public class FeedbackMessageType implements MessageType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.FEEDBACK;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(FEEDBACK_ABOUT_CODE) + lms.getLocaleMessage(FEEDBACK_QUESTION_CODE);
    }
}
