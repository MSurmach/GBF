package com.godeltech.gbf.gui.message.impl;

import com.godeltech.gbf.LocalMessageSource;
import com.godeltech.gbf.gui.message.MessageType;
import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.THANKS_FOR_FEEDBACK_CODE;

@Component
@AllArgsConstructor
public class ThanksForFeedbackMessageType implements MessageType {
    private final LocalMessageSource lms;

    @Override
    public State getState() {
        return State.THANKS_FOR_FEEDBACK;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        return lms.getLocaleMessage(THANKS_FOR_FEEDBACK_CODE, ModelUtils.getUserMention(sessionData));
    }
}
