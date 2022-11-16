package com.godeltech.gbf.gui.text_message.impl;

import com.godeltech.gbf.factory.impl.LocalMessageSourceFactory;
import com.godeltech.gbf.gui.text_message.TextMessageType;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.godeltech.gbf.gui.utils.ConstantUtil.DELETE_FEEDBACK_BY_ID_CODE;

@Component
@AllArgsConstructor
public class FeedbackDeleteByIdTextMessageType implements TextMessageType {
    private final LocalMessageSourceFactory localMessageSourceFactory;

    @Override
    public State getState() {
        return State.FEEDBACK_DELETE_BY_ID;
    }

    @Override
    public String getMessage(SessionData sessionData) {
        LocalMessageSource lms = localMessageSourceFactory.get(sessionData.getLanguage());
        return lms.getLocaleMessage(DELETE_FEEDBACK_BY_ID_CODE);
    }
}
